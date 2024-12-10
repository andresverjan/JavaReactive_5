//+------------------------------------------------------------------+
//|                                                 TSI_lines EA.mq5 |
//|                                                       Joy D Moyo |
//|                                               joydmoyo@gmail.com |
//+------------------------------------------------------------------+
#property copyright "Joy D Moyo"
#property link      "joydmoyo@gmail.com"
#property version   "1.00"

//---Input parameters for EA
input int Stoploss =46;          //Stoploss in pips
input int TakeProfit = 140;      //Takeprofit in pips
input int EA_Magic = 12345;      //EA magic number
input double Lotsize = 0.05;     //Lotsize
input int Slippage = 100;
input bool Buy = false;          //Allow Buying
input bool Sell = true;          //Allow Selling

//---Input parameters for Indicator
//--- input parameters for the mainline
input int      ema1=25;                //First smoothing period
input int      ema2=13;                //Second smoothing period

//--- input parameters for the signal line
input int sMAp = 10;                    //Signal line period
input ENUM_MA_METHOD MAmode = MODE_EMA; //Mode of MA for the signal line

//---Other global parameters
int TSICDHandle;
double TSI_mline[],TSI_sline[];
double STP,TKP;
ulong LastBars = 0;

//+------------------------------------------------------------------+
//| Expert initialization function                                   |
//+------------------------------------------------------------------+
int OnInit()
  {
//---Getting the indicator handles
   TSICDHandle = iCustom(_Symbol,_Period,"TSI_CD",ema1,ema2,sMAp,MAmode);

//---Check if valid handles are returned
   if(TSICDHandle<0)
     {
      Alert("Error creating Handles for indicators -Error: ",GetLastError());
      return(0);
     }

//---Standardise the currency digits for different pairs
   STP = Stoploss*_Point;
   TKP = TakeProfit*_Point;

   if(_Digits==5||_Digits==3)
     {
      STP=STP*10;
      TKP=TKP*10;
     }

//---Checking the adequecy of the number of bars in history
   if(Bars(_Symbol,_Period)<500)
     {
      Alert("We have less than enough bars, EA will now exit");
      return(0);
     }
//---Setting our array to the as series flag
   ArraySetAsSeries(TSI_mline,true);
   ArraySetAsSeries(TSI_sline,true);
   return(INIT_SUCCEEDED);
  }
//+------------------------------------------------------------------+
//| Expert deinitialization function                                 |
//+------------------------------------------------------------------+
void OnDeinit(const int reason)
  {
//---Releasing indicator handles
   IndicatorRelease(TSICDHandle);
  }
//+------------------------------------------------------------------+
//| Expert tick function                                             |
//+------------------------------------------------------------------+
void OnTick()
  {
//---Checking if we are able to trade
   if((!TerminalInfoInteger(TERMINAL_TRADE_ALLOWED))||(!TerminalInfoInteger(TERMINAL_CONNECTED))||(SymbolInfoInteger(_Symbol,SYMBOL_TRADE_MODE)!=SYMBOL_TRADE_MODE_FULL))
     {
      return;
     }

//--- Check if we have a newbar
   ulong bars = Bars(_Symbol,PERIOD_CURRENT);
   if(LastBars!=bars)
     {
      LastBars=bars;
     }
   else
     {
      return;
     }

//---Defining MQL structures to be used for trading
   MqlTick latest_price;                  //To be used to get the latest information about prices
   MqlTradeRequest mrequest;              //To be used to send trade requests
   MqlTradeResult mresult;                //To be used to access trade results
   ZeroMemory(mrequest);

//---Checking if we have the latest price quote
   if(!SymbolInfoTick(_Symbol,latest_price))
     {
      Alert("Error getting the latest price quote - Error: ",GetLastError(),"!!");
      return;
     }

//---Copying and checking indicator values
   if(CopyBuffer(TSICDHandle,2,0,3,TSI_mline)<0||CopyBuffer(TSICDHandle,3,0,3,TSI_sline)<0)
     {
      Alert("Error copying the indicator buffers. Error: ",GetLastError());
      return;
     }
//---Checking for the presence of an open position
   bool Tradeopened = false;

   if(PositionsTotal()>0)
     {
      Tradeopened = true;
     }
//Checking for market entry signals
   bool Buycondition = false;
   bool Sellcondition = false;

//Buy order conditions
   if(Tradeopened==false)
     {
      if(Buy==true)
        {
         if((TSI_mline[1]>TSI_sline[1])&&(TSI_mline[2]<TSI_sline[2]))
           {
            Buycondition=true;
           }
        }
     }
//Sell order conditions
   if(Tradeopened==false)
     {
      if(Sell==true)
        {
         if((TSI_mline[1]<TSI_sline[1])&&(TSI_mline[2]>TSI_sline[2]))
           {
            Sellcondition=true;
           }
        }
     }
//---Executing a buy trade
   if(Buycondition==true)
     {
      mrequest.action=TRADE_ACTION_DEAL;
      mrequest.price=NormalizeDouble(latest_price.ask,_Digits);
      mrequest.sl = NormalizeDouble(latest_price.ask-STP,_Digits);
      mrequest.tp = NormalizeDouble(latest_price.ask+TKP,_Digits);
      mrequest.symbol = _Symbol;
      mrequest.volume = Lotsize;
      mrequest.magic = EA_Magic;
      mrequest.type = ORDER_TYPE_BUY;
      mrequest.type_filling = ORDER_FILLING_FOK;
      mrequest.deviation = Slippage;

      bool buyorder = OrderSend(mrequest,mresult);

      //---Getting the trade results
      if(mresult.retcode==10009||mresult.retcode==10008)
        {
         Alert("A buy order has been successfully placed with ticket# :",mresult.order,"!!");
        }
      else
        {
         Alert("A buy trade could not be placed -Error: ",GetLastError());
        }
     }

//---Executing a sell trade
   if(Sellcondition==true)
     {
      mrequest.action=TRADE_ACTION_DEAL;
      mrequest.price=NormalizeDouble(latest_price.bid,_Digits);
      mrequest.sl = NormalizeDouble(latest_price.bid+STP,_Digits);
      mrequest.tp = NormalizeDouble(latest_price.bid-TKP,_Digits);
      mrequest.symbol = _Symbol;
      mrequest.volume = Lotsize;
      mrequest.magic = EA_Magic;
      mrequest.type = ORDER_TYPE_SELL;
      mrequest.type_filling = ORDER_FILLING_FOK;
      mrequest.deviation = Slippage;

      bool sellorder = OrderSend(mrequest,mresult);

      //---Getting the trade results
      if(mresult.retcode==10009||mresult.retcode==10008)
        {
         Alert("A sell order has been successfully placed with ticket# :",mresult.order,"!!");
        }
      else
        {
         Alert("A sell order could not be placed. Error: ",GetLastError());
        }
     }
  }
//+------------------------------------------------------------------+
