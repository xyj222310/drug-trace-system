/**
*@file MFRC522.cpp
*@author 
*@version v0.1
*@brief 串口驱动MFRC522，使用时引入 string.h ,由于需要使用USART2，所以这个类直接把USART2接管了，为了课程设计也没有必要去更改。
*
*/


#include "MFRC522.h"
#include "string.h"
USART usart2test(2,9600);//默认9600
//USART usart1test(1,115200);//用来测试是否是

MFRC522 mfrc522;


/******************************************************************************
 *@brief：构造函数
 *@param：无
 *@retval：无
 ******************************************************************************/

MFRC522::MFRC522()
{
	;
}




/******************************************************************************
 *@brief：初始化RC522
 *@param：无
 *@retval：无
 ******************************************************************************/
void MFRC522::init()
{
  reset();

  //Timer: TPrescaler*TreloadVal/6.78MHz = 24ms
	//Tauto=1; f(Timer) = 6.78MHz/TPreScaler
	//TModeReg[3..0] + TPrescalerReg
  writeMFRC522(TModeReg, 0x8D);  
  writeMFRC522(TPrescalerReg, 0x3E); 
  writeMFRC522(TReloadRegL, 30);
  writeMFRC522(TReloadRegH, 0);
  writeMFRC522(TxAutoReg, 0x40);    //100%ASK
  writeMFRC522(ModeReg, 0x3D);    // CRC valor inicial de 0x6363

  //ClearBitMask(Status2Reg, 0x08); //MFCrypto1On=0
  //writeMFRC522(RxSelReg, 0x86);   //RxWait = RxSelReg[5..0]
  //writeMFRC522(RFCfgReg, 0x7F);     //RxGain = 48dB

//  antennaOn();    //打开天线
}

/******************************************************************************
 *@brief：寻卡
 *@param：无
 *@retval：有卡，返回true，无卡，返回false
 ******************************************************************************/
bool MFRC522::isCard()
{
  unsigned char status;											//获得MFRC返回的状态
  unsigned char str[MAX_LEN];
//	status = MFRC522Request(PICC_REQALL, str);//读取所有的卡
  status = MFRC522Request(PICC_REQIDL, str);
  if (status == MI_OK)
    return true;
  else
    return false;
}

/******************************************************************************
 *@brief：寻卡，读取卡类型号
 *@param：reqMode--寻卡方式，
 *        TagType--返回卡片类型
 *                    0x4400 = Mifare_UltraLight
 *                    0x0400 = Mifare_One(S50)
 *                    0x0200 = Mifare_One(S70)
 *                    0x0800 = Mifare_Pro(X)
 *                    0x4403 = Mifare_DESFire
 *@retval：成功返回MI_OK
 ******************************************************************************/

unsigned char MFRC522::MFRC522Request(unsigned char reqMode, unsigned char *TagType)
{
  unsigned char status;
  unsigned int backBits;      //接收到的数据位数

  writeMFRC522(BitFramingReg, 0x07);    //TxLastBists = BitFramingReg[2..0] ???表示发送最后一个字节的最高位

  TagType[0] = reqMode;
  status = MFRC522ToCard(PCD_TRANSCEIVE, TagType, 1, TagType, &backBits);
//	printTest(TagType,2);//拿到的三张白卡全是0400

  if ((status != MI_OK) || (backBits != 0x10))
    status = MI_ERR;

  return status;
}


/******************************************************************************
 *@brief：向MFRC522的某一寄存器写一个字节数据
 *					第七位作为控制读写，1为读，0为写
 *					第六位保留
 *					0-5位为寄存器的值
 *@param：addr--寄存器地址；val--要写入的值
 *@retval：无
 ******************************************************************************/
void MFRC522::writeMFRC522(unsigned char addr, unsigned char val)
{
unsigned char writeaddval[2] = {addr,val};
unsigned char reciveval = 0x01;
  
	usart2test.SendData(writeaddval,1);//发送地址和数据过去
	for(uint32_t i=0;i<100000;++i);
	if(usart2test.ReceiveBufferSize()>0)
	{
		usart2test.GetReceivedData(&reciveval,1);

	}
	if(reciveval == addr)//如果返回回来的值是地址，就表明访问成功
	{
		usart2test.SendData(&val,1);//发送地址和数据过去
	}
}
/******************************************************************************
 *@brief：从MFRC522的某一寄存器读一个字节数据
 *					第七位作为控制读写，1为读，0为写
 *					第六位保留
 *					0-5位为寄存器的值
 *@param：addr--寄存器地址
 *@retval：返回读取到的一个字节数据
 ******************************************************************************/
unsigned char MFRC522::readMFRC522(u8 addr)
{
	unsigned char reciveval[1] = {0x01};
  u8 reeaaddre = (addr|0x80);

  usart2test.SendData(&reeaaddre,1);
	for(uint32_t i=0;i<100000;++i);
	if(usart2test.ReceiveBufferSize()>0)
	{
//		int receivedDataNum=usart2test.ReceiveBufferSize();
		usart2test.GetReceivedData(reciveval,1);
	}
	//////////////////////////////////////
	///测试的时候用usart1打印出来
	///////////////////////////////////
//	usart1test.SendData(reciveval,1);
  return reciveval[0];
}

/******************************************************************************
 *@brief：通过RC522和ISO14443卡通讯
 *@param：Command[IN]:RC522命令字
 *          pInData[IN]:通过RC522发送到卡片的数据
 *          InLenByte[IN]:发送数据的字节长度
 *          pOutData[OUT]:接收到的卡片返回数据
 *          *pOutLenBit[OUT]:返回数据的位长度
 *@retval：成功返回MI_OK
 ******************************************************************************/
unsigned char MFRC522::MFRC522ToCard(unsigned char Command, 
                 unsigned char *pInData, 
                 unsigned char InLenByte,
                 unsigned char *pOutData, 
                 unsigned int  *pOutLenBit)
{
    char status = MI_ERR;
    unsigned char irqEn   = 0x00;
    unsigned char waitFor = 0x00;
    unsigned char lastBits;
    unsigned char n;
    unsigned int i;
		unsigned int MAXRLEN = 18;
    switch (Command)
    {
       case PCD_AUTHENT:
          irqEn   = 0x12;
          waitFor = 0x10;
          break;
       case PCD_TRANSCEIVE:
          irqEn   = 0x77;
          waitFor = 0x30;
          break;
       default:
         break;
    }
   
    writeMFRC522(CommIEnReg,irqEn|0x80);
    clearBitMask(CommIrqReg,0x80);
    writeMFRC522(CommandReg,PCD_IDLE);
    setBitMask(FIFOLevelReg,0x80);
    
    for (i=0; i<InLenByte; i++)
    {   writeMFRC522(FIFODataReg, pInData[i]);    }
    writeMFRC522(CommandReg, Command);
   
    
    if (Command == PCD_TRANSCEIVE)
    {    setBitMask(BitFramingReg,0x80);  }
    
    i = 600;//根据时钟频率调整，操作M1卡最大等待时间25ms
    do 
    {
         n = readMFRC522(CommIrqReg);
         i--;
    }
    while ((i!=0) && !(n&0x01) && !(n&waitFor));
    clearBitMask(BitFramingReg,0x80);
	      
    if (i!=0)
    {    
         if(!(readMFRC522(ErrorReg)&0x1B))
         {
             status = MI_OK;
             if (n & irqEn & 0x01)
             {   status = MI_NOTAGERR;   }
             if (Command == PCD_TRANSCEIVE)
             {
               	n = readMFRC522(FIFOLevelReg);
              	lastBits = readMFRC522(ControlReg) & 0x07;
                if (lastBits)
                {   *pOutLenBit = (n-1)*8 + lastBits;   }
                else
                {   *pOutLenBit = n*8;   }
                if (n == 0)
                {   n = 1;    }
                if (n > MAXRLEN)
                {   n = MAXRLEN;   }
                for (i=0; i<n; i++)
                {   pOutData[i] = readMFRC522(FIFODataReg);    }
            }
         }
         else
         {   status = MI_ERR;   }
        
   }
   

   setBitMask(ControlReg,0x80);           // stop timer now
   writeMFRC522(CommandReg,PCD_IDLE); 
   return status;
}

///******************************************************************************
// * 函 数 名：MFRC522ToCard
// * 功能描述：RC522和ISO14443卡通讯
// * 输入参数：command--MF522命令字，
// *           sendData--通过RC522发送到卡片的数据,
// *                     sendLen--发送的数据长度
// *                     backData--接收到的卡片返回数据，
// *                     backLen--返回数据的位长度
// * 返 回 值：成功返回MI_OK
// ******************************************************************************/
//unsigned char MFRC522::MFRC522ToCard(unsigned char command, 
//																			unsigned char *sendData, 
//																				unsigned char sendLen, 
//																					unsigned char *backData, 
//																						unsigned int *backLen)
//{
//  unsigned char status = MI_ERR;
//  unsigned char irqEn = 0x00;
//  unsigned char waitIRq = 0x00;
//  unsigned char lastBits;
//  unsigned char n;
//  unsigned int i;
//	
//			u8 str = 0xaa;//测试数据

//  switch (command)
//  {
//    case PCD_AUTHENT:   //认证卡密
//    {
//      irqEn = 0x12;
//      waitIRq = 0x10;
//      break;
//    }
//    case PCD_TRANSCEIVE:  //发送FIFO中数据
//    {
//      irqEn = 0x77;
//      waitIRq = 0x30;
//      break;
//    }
//    default:
//      break;
//  }

//  writeMFRC522(CommIEnReg, irqEn|0x80); //允许中断请求
//  clearBitMask(CommIrqReg, 0x80);       //清除所有中断请求位
//  setBitMask(FIFOLevelReg, 0x80);       //FlushBuffer=1, FIFO初始化

//  writeMFRC522(CommandReg, PCD_IDLE);   //无动作，取消当前命令

//  //向FIFO中写入数据
//  for (i=0; i<sendLen; i++)
//    writeMFRC522(FIFODataReg, sendData[i]);

//  //执行命令
//  writeMFRC522(CommandReg, command);
//  if (command == PCD_TRANSCEIVE)
//    setBitMask(BitFramingReg, 0x80);    //StartSend=1,transmission of data starts

//  //等待接收数据完成
//  i = 2000; //i根据时钟频率调整，操作M1卡最大等待时间25ms
//  do
//  {
//    //CommIrqReg[7..0]
//    //Set1 TxIRq RxIRq IdleIRq HiAlerIRq LoAlertIRq ErrIRq TimerIRq
//    n = readMFRC522(CommIrqReg);
//    i--;
//  }
//  while ((i!=0) && !(n&0x01) && !(n&waitIRq));

//  clearBitMask(BitFramingReg, 0x80);      //StartSend=0

//  if (i != 0)
//  {

//    if(!(readMFRC522(ErrorReg) & 0x1B)) //BufferOvfl Collerr CRCErr ProtecolErr
//    {
//      status = MI_OK;
//      if (n & irqEn & 0x01)
//        status = MI_NOTAGERR;     //判断是否有卡

//      if (command == PCD_TRANSCEIVE)
//      {
//        n = readMFRC522(FIFOLevelReg);
//        lastBits = readMFRC522(ControlReg) & 0x07;
//        if (lastBits)
//          *backLen = (n-1)*8 + lastBits;
//        else
//          *backLen = n*8;

//        if (n == 0)
//          n = 1;
//        if (n > MAX_LEN)
//          n = MAX_LEN;

//        //读取FIFO中接收到的数据
//        for (i=0; i<n; i++)
//          backData[i] = readMFRC522(FIFODataReg);
//      }
//    }
//    else
//		{
//			if(!(readMFRC522(CollReg)&0x20))//1&1 = 1 0&1 = 0,0代表有冲突，1代表没冲突
//			{

//				u8 where = readMFRC522(CollReg)&0x1f;//存储第一个出现了冲突的地方
////				printTest(&where,1); 
//			//发送碰撞，进入防冲撞算法
//			 if (command == PCD_TRANSCEIVE)
//      {
//        n = readMFRC522(FIFOLevelReg);
//        lastBits = readMFRC522(ControlReg) & 0x07;//存储最后接收到的自己的有效位数目
//        if (lastBits)
//          *backLen = (n-1)*8 + lastBits;
//        else
//          *backLen = n*8;

//        if (n == 0)
//          n = 1;
//        if (n > MAX_LEN)
//          n = MAX_LEN;

//        //读取FIFO中接收到的数据
//        for (i=0; i<n; i++)
//          backData[i] = readMFRC522(FIFODataReg);
//      }
////			printTest(&str,1);
////			printTest(backData,n);
//      status = MI_ERR;
//		}
//	}
//  }

//  //SetBitMask(ControlReg,0x80);           //timer stops
//  //Write_MFRC522(CommandReg, PCD_IDLE);

//  return status;
//}
/******************************************************************************
 *@brief：清RC522寄存器位
 *@param：reg--寄存器地址;mask--清位值
 *@retval：无
 ******************************************************************************/
void MFRC522::clearBitMask(unsigned char reg, unsigned char mask)
{
  unsigned char tmp;
  tmp = readMFRC522(reg);
  writeMFRC522(reg, tmp & (~mask));  // clear bit mask
}

/******************************************************************************
 *@brief：置位RC522寄存器
 *@param：reg--寄存器地址;mask--置位值
 *@retval：无
 ******************************************************************************/
void MFRC522::setBitMask(unsigned char reg, unsigned char mask)
{
  unsigned char tmp;
  tmp = readMFRC522(reg);
  writeMFRC522(reg, tmp | mask);  // set bit mask
}


/******************************************************************************
 *@brief：软件复位RC522
 *@param：无
*@retval：无
 ******************************************************************************/
void MFRC522::reset()
{
  writeMFRC522(CommandReg, PCD_RESETPHASE);
}
/******************************************************************************
 *@brief：开启天线,每次启动或关闭天险发射之间应至少有1ms的间隔
 *@param：无
 *@retval：无
 ******************************************************************************/
void MFRC522::antennaOn(void)
{
  unsigned char temp;

  temp = readMFRC522(TxControlReg);
  if (!(temp & 0x03))
  {
    setBitMask(TxControlReg, 0x03);
  }
}
/******************************************************************************
 *@brief：关闭天线,每次启动或关闭天险发射之间应至少有1ms的间隔
 *@param：无
 *@retval：无
 ******************************************************************************/
void MFRC522::antennaOff(void)
{
  unsigned char temp;

  temp = readMFRC522(TxControlReg);
  if (!(temp & 0x03))
  {
    clearBitMask(TxControlReg, 0x03);
  }
}
/******************************************************************************
*@brief：返回卡的序列号 4字节
*@param：无
*@retval：成功返回ture 失败返回false
 ******************************************************************************/
bool MFRC522::readCardSerial(){

  unsigned char status;
  unsigned char str[MAX_LEN];
  
  // 防冲撞，返回卡的序列号 4字节，存入serNum中
  status = anticoll(str);
  memcpy(serNum, str, 5);
  
  if (status == MI_OK)
    return true;
  else
    return false;
}
/******************************************************************************
 *@brief：防冲突检测，读取选中卡片的卡序列号
 *@param：serNum--返回4字节卡序列号,第5字节为校验字节
 *@retval：成功返回MI_OK
 ******************************************************************************/
unsigned char MFRC522::anticoll(unsigned char *serNum)
{
  unsigned char status;
  unsigned char i;
  unsigned char serNumCheck=0;
  unsigned int unLen;

//  clearBitMask(Status2Reg, 0x08);   //TempSensclear
//  clearBitMask(CollReg,0x80);     //ValuesAfterColl
  writeMFRC522(BitFramingReg, 0x00);    //TxLastBists = BitFramingReg[2..0]

  serNum[0] = PICC_ANTICOLL;
  serNum[1] = 0x20;
  status = MFRC522ToCard(PCD_TRANSCEIVE, serNum, 2, serNum, &unLen);

  if (status == MI_OK)
  {
    //校验卡序列号
    for (i=0; i<4; i++)
      serNumCheck ^= serNum[i];
    if (serNumCheck != serNum[i])
      status = MI_ERR;
  }

  //SetBitMask(CollReg, 0x80);    //ValuesAfterColl=1

  return status;
}



/******************************************************************************
 *@brief：命令卡片进入休眠状态
 *@param：无
 *@retval：无
 ******************************************************************************/
void MFRC522::halt()
{
  unsigned char status;
  unsigned int unLen;
  unsigned char buff[4];

  buff[0] = PICC_HALT;
  buff[1] = 0;
  calculateCRC(buff, 2, &buff[2]);

  status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 4, buff,&unLen);//将发送数据的命令传入MFRC，把休眠的命令传给卡
}


/******************************************************************************
 *@brief：选择性Halt
 *@brief：命令卡片进入休眠状态
 *@param：无
 *@retval：无
 ******************************************************************************/
void MFRC522::haltselect(unsigned char* str)
{
  unsigned int unLen;
	unsigned char status;
  unsigned char buff[6];

  buff[0] = PICC_HALT;
  buff[1] = *str;
	buff[2] = *str+1;
	buff[3] = *str+2;
	buff[4] = *str+3;
	buff[5] = *str+4;
//  calculateCRC(buff, 2, &buff[2]);

  status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 4, buff,&unLen);//将发送数据的命令传入MFRC，把休眠的命令传给卡
}


/******************************************************************************
 *@brief：用MF522计算CRC
 *@param：pIndata--要读数CRC的数据，len--数据长度，pOutData--计算的CRC结果
 *@retval：无
 ******************************************************************************/
void MFRC522::calculateCRC(unsigned char *pIndata, unsigned char len, unsigned char *pOutData)
{
  unsigned char i, n;

  clearBitMask(DivIrqReg, 0x04);      //CRCIrq = 0
  setBitMask(FIFOLevelReg, 0x80);     //清FIFO指针
  //Write_MFRC522(CommandReg, PCD_IDLE);

  //向FIFO中写入数据
  for (i=0; i<len; i++)
    writeMFRC522(FIFODataReg, *(pIndata+i));
  writeMFRC522(CommandReg, PCD_CALCCRC);

  //等待CRC计算完成
  i = 0xFF;
  do
  {
    n = readMFRC522(DivIrqReg);
    i--;
  }
  while ((i!=0) && !(n&0x04));      //CRCIrq = 1

  //读取CRC计算结果
  pOutData[0] = readMFRC522(CRCResultRegL);
  pOutData[1] = readMFRC522(CRCResultRegM);
}
/******************************************************************************
 *@brief：选卡，读取卡存储器容量
 *@param：serNum--传入卡序列号
 *@retval：成功返回卡容量
 ******************************************************************************/
unsigned char MFRC522::selectTag(unsigned char *serNum)
{
  unsigned char i;
  unsigned char status;
  unsigned char size;
  unsigned int recvBits;
  unsigned char buffer[9];

  //ClearBitMask(Status2Reg, 0x08);                        //MFCrypto1On=0

  buffer[0] = PICC_SElECTTAG;
  buffer[1] = 0x70;

  for (i=0; i<5; i++)
    buffer[i+2] = *(serNum+i);

  calculateCRC(buffer, 7, &buffer[7]);
  
  status = MFRC522ToCard(PCD_TRANSCEIVE, buffer, 9, buffer, &recvBits);
  if ((status == MI_OK) && (recvBits == 0x18))
    size = buffer[0];
  else
    size = 0;
  return size;
}

/******************************************************************************
 *@brief：验证卡片密码
 *@param：authMode--密码验证模式
 *                     0x60 = 验证A密钥
 *                     0x61 = 验证B密钥
 *           BlockAddr--块地址
 *           Sectorkey--扇区密码
 *           serNum--卡片序列号，4字节
 *@retval：成功返回MI_OK
 ******************************************************************************/
unsigned char MFRC522::auth(unsigned char authMode, unsigned char BlockAddr, unsigned char *Sectorkey, unsigned char *serNum)
{
  unsigned char status;
  unsigned int recvBits;
  unsigned char i;
  unsigned char buff[12];

  //验证指令+块地址＋扇区密码＋卡序列号
  buff[0] = authMode;
  buff[1] = BlockAddr;
  for (i=0; i<6; i++)
    buff[i+2] = *(Sectorkey+i);
  for (i=0; i<4; i++)
    buff[i+8] = *(serNum+i);
    
  status = MFRC522ToCard(PCD_AUTHENT, buff, 12, buff, &recvBits);//其实是认证成功了的				
//  if ((status != MI_OK) || (!(readMFRC522(Status2Reg) & 0x08)))
//    status = MI_ERR;
  if ((status != MI_OK))
    status = MI_ERR;
  return status;
}
/******************************************************************************
 *@brief：读块数据
 *@param：blockAddr--块地址;recvData--读出的块数据
 *@retval：成功返回MI_OK
 ******************************************************************************/
unsigned char MFRC522::read(unsigned char blockAddr, unsigned char *recvData)
{
  unsigned char status;
  unsigned int unLen;

  recvData[0] = PICC_READ;
  recvData[1] = blockAddr;
  calculateCRC(recvData,2, &recvData[2]);
  status = MFRC522ToCard(PCD_TRANSCEIVE, recvData, 4, recvData, &unLen);

  if ((status != MI_OK) || (unLen != 0x90))
    status = MI_ERR;

  return status;
}
/******************************************************************************
 *@brief：写块数据
 *@param：blockAddr--块地址;writeData--向块写16字节数据
 *@retval：成功返回MI_OK
 ******************************************************************************/
unsigned char MFRC522::write(unsigned char blockAddr, unsigned char *writeData)
{
  unsigned char status;
  unsigned int recvBits;
  unsigned char i;
  unsigned char buff[18];

  buff[0] = PICC_WRITE;
  buff[1] = blockAddr;
  calculateCRC(buff, 2, &buff[2]);
  status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 4, buff, &recvBits);

  if ((status != MI_OK) || (recvBits != 4) || ((buff[0] & 0x0F) != 0x0A))
    status = MI_ERR;

  if (status == MI_OK)
  {
    for (i=0; i<16; i++)    //?FIFO?16Byte?? Datos a la FIFO 16Byte escribir
      buff[i] = *(writeData+i);
      
    calculateCRC(buff, 16, &buff[16]);
    status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 18, buff, &recvBits);

    if ((status != MI_OK) || (recvBits != 4) || ((buff[0] & 0x0F) != 0x0A))
      status = MI_ERR;
  }

  return status;
}
















