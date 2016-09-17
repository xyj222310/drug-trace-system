#include "USART.h"
#include "TaskManager.h"
//#include "MFRC522.h"
#include "MFRC522.h"


/**
*@file 		课程设计对MFCR522的demo
*@author 	
*@version v0.1
*@brief 	
*/



//原扇区A密码，16个扇区，每个扇区密码6Byte
unsigned char DefaultKey[6] = {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF}; 
//unsigned char DefaultKey[6] = {0xcc, 0xcc, 0xcc, 0xcc, 0xcc, 0xcc}; 

unsigned char sector[16]={0,4,8,12,16,20,24,28,32,36,40,44,48,52,56,60}; //扇区的首地址

USART usart(1,115200);


//4字节卡序列号，第5字节为校验字节

unsigned char i,tmp;
unsigned char status;
unsigned char status2;
unsigned char blockAddr;     //选择操作的块地址0～63
u8 sendDataBuffer[24];       //定义发送缓冲，每次发送24字节

u8 readsuccess  = 0xca;      //认证RFID卡信息成功
u8 readfail     = 0xcf;      //认证RFID卡信息失败

u8 writesuccess = 0xcb;      //写RFID卡信息成功

u8 checkcard    = 0xc0;      //是否有卡？
u8 checkread    = 0xc1;      //是否可读？


unsigned char Receive[20] = {};   //接收的缓冲
unsigned char ReceiveStr[10][16]={};  //此处最多写10个块
unsigned char EndStr[24]={0xaa,0xaa,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff
                         ,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff};  //结束帧

u8 SendbackDataBuffer[24];							 

//串口配置MFRC522
												 
/*
 *checkSum 和校验，返回一组数据的和												 
 *param *sendDataBuffer  需要求和的数组
 *param size  需要发送数据的数组的长度
												 */
u8 checkSum(u8 *sendDataBuffer,char size) //和校验
{
	u32 sum=0;
	unsigned char i;
	for(i=0;i<size;i++)
	{
		sum += sendDataBuffer[i];
	}
	return sum%256;
}

/*
 *checkSum 和校验，若和校验通过，返回true，若和校验失败，返回false												 
 *param *RecivieDataBuffer  需验证和校验的的数组
 *param size  需要校验和校验数据的数组的长度
												 */
bool checkSumIsOk(u8 *RecivieDataBuffer,char size) //20
{
	u32 sum=0;
	unsigned char i;
	for(i=0;i<size-1;i++)  // i到18
	{
		sum += RecivieDataBuffer[i];
	}
	u8 sum1 = sum%256;
	if(sum1 == RecivieDataBuffer[size-1])
	{
		return true;
	}
	else
	{
		return false;
	}
}


/*
 * initSendDataBuffer 初始化需要发送的发送数组，让它成功完整的一帧（有帧头，功能码，数据，和校验）
 * param fun_number 功能码，说明这个数组的来历
*/
void initSendDataBuffer(unsigned char fun_number)  //初始化发送数组
{
	 unsigned char x;
	 unsigned char y;
	 u8 sum1;
	 sendDataBuffer[0] = 0xaa;
	 sendDataBuffer[1] = 0xaa;
	 sendDataBuffer[2] = fun_number;
	 for(x = 3;x<7;x++)
	 {
		  sendDataBuffer[x] = mfrc522.serNum[x-3];
	 }
	 for(y = 7;y<23;y++)
	 {
		  sendDataBuffer[y] = mfrc522.ReadStr[y-7];
	 }
	 sum1 = checkSum(sendDataBuffer,23);
	 sendDataBuffer[23] = sum1;
}

/*
 * 
*/
void initSendbackDataBuffer(unsigned char fun_number,unsigned char e)  //初始化修改确认发送数组
{
	 unsigned char x;
	 unsigned char y;
	 u8 sum1;
	 SendbackDataBuffer[0] = 0xaa;
	 SendbackDataBuffer[1] = 0xaa;
	 SendbackDataBuffer[2] = fun_number;
	 for(x = 3;x<7;x++)
	 {
		  SendbackDataBuffer[x] = mfrc522.serNum[x-3];
	 }
	 for(y = 7;y<23;y++)
	 {
		  SendbackDataBuffer[y] = ReceiveStr[e][y-7];
	 }
	 sum1 = checkSum(SendbackDataBuffer,23);
	 SendbackDataBuffer[23] = sum1;
}

/*
 * CheckBlock 检查块号是否可用，
*/
void CheckBlock(unsigned char &block)
{
	if((block+1)%4!=0)  
	{
		block++;
		if((block+1)%4 == 0)
		{
			block++;
		}
	}
}

/*
 *MakeSendDataToCardBuffer 解析写入RFID卡数据
 * param ReceiveBuffer 从串口接收到的数组
*/
unsigned char MakeSendDataToCardBuffer(unsigned char *ReceiveBuffer)  //解析写入RFID卡的数据
{
	  unsigned char position=0;
	  if(ReceiveBuffer[0] == 0xaa && ReceiveBuffer[1] == 0xaa && ReceiveBuffer[2] ==0x01)  //写入药品
		{
			 for(position=0;position<16;position++)  //向RFID卡写入16个字节
			 {
				  ReceiveStr[0][position] = ReceiveBuffer[position+3];
			 }
			 return 4;          //返回应写入第4块
		}
//		if(ReceiveBuffer[0] == 0xaa && ReceiveBuffer[1] == 0xaa && ReceiveBuffer[2] ==0x01)  //写入销售商
//		{
//			 for(position=0;position<16;position++)  //向RFID卡写入16个字节
//			 {
//				  ReceiveStr[0][position] = ReceiveBuffer[position+3];
//			 }
//			 return 4;          //返回应写入第4块
//		}
		
		if(ReceiveBuffer[0] == 0xaa && ReceiveBuffer[1] == 0xaa && ReceiveBuffer[2] ==0x02)  //写入工厂
		{
			 for(position=0;position<16;position++)  //向RFID卡写入16个字节
			 {
				  ReceiveStr[1][position] = ReceiveBuffer[position+3];
			 }
			 return 5;           //返回应写入第5块
		}
		if(ReceiveBuffer[0] == 0xaa && ReceiveBuffer[1] == 0xaa && ReceiveBuffer[2] ==0x03)  //写入运输商
		{
			 for(position=0;position<16;position++)  //向RFID卡写入16个字节
			 {
				  //ReceiveStr[2][position] = ReceiveBuffer[position+3];
				 ReceiveStr[2][position] = ReceiveBuffer[position+3];
			 }
			 return 6;               //返回应写入第6块
		}
		
		return 63;
}


int main()
{
	tskmgr.DelayMs(2000);
	mfrc522.init();							//发送初始化等命令
	tskmgr.DelayMs(2);					//等待2ms之后打开天线
	mfrc522.antennaOn();				//打开天线
	tskmgr.DelayMs(2);
	
	while(1)
	{
		usart.ClearReceiveBuffer();
		mfrc522.isCard();
		if(mfrc522.readCardSerial())
		{
			usart.SendData(&checkcard,1);     //发送附近有卡存在信息
			usart.SendData(&checkread,1);     //发送还卡可读信息
			usart.SendData(mfrc522.serNum,5); //马上输出卡的ID  改，与读出的信息一起发送出去
		}
		mfrc522.selectTag(mfrc522.serNum);
		blockAddr = sector[1];                //数据块4  此处也当做了基础数据块,第一个扇区被我玩坏了
		                              //(i+1)%4==0  不能写   有(3,7,11,15....)
		status = mfrc522.auth(PICC_AUTHENT1A, blockAddr, DefaultKey, mfrc522.serNum);//返回认证的状态
		if (status == MI_OK)
		{
			  if(mfrc522.read(blockAddr,mfrc522.ReadStr) == MI_OK)    //药名+销售信息
				{
					initSendDataBuffer(0x01);
					usart.SendData(sendDataBuffer,24);
				}
				CheckBlock(blockAddr);
				if(mfrc522.read(blockAddr,mfrc522.ReadStr) == MI_OK)    //工厂信息
				{
					initSendDataBuffer(0x02);
					usart.SendData(sendDataBuffer,24);
				}
				CheckBlock(blockAddr);
				if(mfrc522.read(blockAddr,mfrc522.ReadStr) == MI_OK)    //运输商信息
				{
					initSendDataBuffer(0x03);
					usart.SendData(sendDataBuffer,24);
					if(usart.ReceiveBufferSize()<20)         //缓冲区没有数据
				  {
					  usart.SendData(EndStr,24); //发送结束帧
				  }
				}
				
				if(usart.ReceiveBufferSize()>=20)  //如果缓冲区的大小大于20
				{
					// usart.ClearReceiveBuffer();
					usart.GetReceivedData(Receive,20);
					blockAddr = MakeSendDataToCardBuffer(Receive);
					if(blockAddr == 4)
					{
						if(mfrc522.write(blockAddr,ReceiveStr[0]) == MI_OK)   //写入成功
						{
							initSendbackDataBuffer(0x01,0);
							usart.SendData(SendbackDataBuffer,24);   
							usart.SendData(EndStr,24);							
						}
					}
					if(blockAddr == 5)
					{
						if(mfrc522.write(blockAddr,ReceiveStr[1]) == MI_OK)   //写入成功
						{
							 initSendbackDataBuffer(0x02,1);
							 usart.SendData(SendbackDataBuffer,24); 
							 usart.SendData(EndStr,24);							
						}
					}
					if(blockAddr == 6)
					{
						if(mfrc522.write(blockAddr,ReceiveStr[2]) == MI_OK)   //写入成功
						{
							 initSendbackDataBuffer(0x03,2);
							 usart.SendData(SendbackDataBuffer,24);  
							 usart.SendData(EndStr,24);							
						}
				 }
		   }
				
		}
		else
			{
				usart.SendData(&readfail,1);  //发送认证失败信息
			}
			
			mfrc522.halt();
	}
		
	return 0;
	
}



