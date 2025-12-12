package b4j.example;


import anywheresoftware.b4a.BA;

public class main extends Object{
public static main mostCurrent = new main();

public static BA ba;
static {
		ba = new  anywheresoftware.b4a.StandardBA("b4j.example", "b4j.example.main", null);
		ba.loadHtSubs(main.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "b4j.example.main", ba);
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}

 
    public static void main(String[] args) throws Exception{
        try {
            anywheresoftware.b4a.keywords.Common.LogDebug("Program started.");
            initializeProcessGlobals();
            ba.raiseEvent(null, "appstart", (Object)args);
        } catch (Throwable t) {
			BA.printException(t, true);
		
        } finally {
            anywheresoftware.b4a.keywords.Common.LogDebug("Program terminated (StartMessageLoop was not called).");
        }
    }
public static anywheresoftware.b4a.keywords.Common __c = null;
public static String _jdata = "";
public static anywheresoftware.b4a.objects.Timer _livetimer = null;
public static String _server = "";
public static String _lserver = "";
public static String _site = "";
public static anywheresoftware.b4a.objects.collections.List _datelist = null;
public static anywheresoftware.b4a.objects.collections.List _idlist = null;
public static anywheresoftware.b4a.objects.collections.List _topicslist = null;
public static anywheresoftware.b4a.objects.collections.List _mainlist = null;
public static String _setlotto = "";
public static long _setlottotime = 0L;
public static b4j.example.main._tiemandset _ts = null;
public static String _stockserver = "";
public static anywheresoftware.b4a.objects.Timer _timer = null;
public static b4j.example.calculate _calculate = null;
public static b4j.example.httputils2service _httputils2service = null;
public static class _tiemandset{
public boolean IsInitialized;
public boolean isOpen;
public boolean isMorning;
public void Initialize() {
IsInitialized = true;
isOpen = false;
isMorning = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _appstart(String[] _args) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub AppStart (Args() As String)";
 //BA.debugLineNum = 28;BA.debugLine="DateTime.DateFormat = \"yyyy-MM-dd\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy-MM-dd");
 //BA.debugLineNum = 29;BA.debugLine="Log(\"Hello world!!!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("265539","Hello world!!!",0);
 //BA.debugLineNum = 30;BA.debugLine="timer.Initialize(\"timer\",1000)";
_timer.Initialize(ba,"timer",(long) (1000));
 //BA.debugLineNum = 31;BA.debugLine="timer.Enabled = True";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 32;BA.debugLine="ts.isMorning=False";
_ts.isMorning /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 33;BA.debugLine="ts.isOpen=True";
_ts.isOpen /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 34;BA.debugLine="download";
_download();
 //BA.debugLineNum = 35;BA.debugLine="StartMessageLoop";
anywheresoftware.b4a.keywords.Common.StartMessageLoop(ba);
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public static String  _dlp(String _link) throws Exception{
b4j.example.httpjob _j = null;
 //BA.debugLineNum = 478;BA.debugLine="Sub dlp (link As String)";
 //BA.debugLineNum = 479;BA.debugLine="Dim j As HttpJob";
_j = new b4j.example.httpjob();
 //BA.debugLineNum = 480;BA.debugLine="j.Initialize(\"dlp\",Me)";
_j._initialize /*String*/ (ba,"dlp",main.getObject());
 //BA.debugLineNum = 481;BA.debugLine="j.Download(link)";
_j._download /*String*/ (_link);
 //BA.debugLineNum = 483;BA.debugLine="End Sub";
return "";
}
public static String  _download() throws Exception{
b4j.example.httpjob _j = null;
 //BA.debugLineNum = 92;BA.debugLine="Sub download";
 //BA.debugLineNum = 93;BA.debugLine="Dim j As HttpJob";
_j = new b4j.example.httpjob();
 //BA.debugLineNum = 94;BA.debugLine="j.Initialize(\"getter\",Me)";
_j._initialize /*String*/ (ba,"getter",main.getObject());
 //BA.debugLineNum = 95;BA.debugLine="j.Download(site)";
_j._download /*String*/ (_site);
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _downloaddata(String _datauri,anywheresoftware.b4a.objects.collections.Map _m) throws Exception{
b4j.example.httpjob _j = null;
 //BA.debugLineNum = 84;BA.debugLine="Sub downloaddata (datauri As String,m As Map)";
 //BA.debugLineNum = 85;BA.debugLine="Dim j As HttpJob";
_j = new b4j.example.httpjob();
 //BA.debugLineNum = 86;BA.debugLine="j.Initialize(\"datagetter\",Me)";
_j._initialize /*String*/ (ba,"datagetter",main.getObject());
 //BA.debugLineNum = 87;BA.debugLine="j.Download(datauri)";
_j._download /*String*/ (_datauri);
 //BA.debugLineNum = 89;BA.debugLine="j.Tag=m";
_j._tag /*Object*/  = (Object)(_m.getObject());
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static void  _jobdone(b4j.example.httpjob _job) throws Exception{
ResumableSub_JobDone rsub = new ResumableSub_JobDone(null,_job);
rsub.resume(ba, null);
}
public static class ResumableSub_JobDone extends BA.ResumableSub {
public ResumableSub_JobDone(b4j.example.main parent,b4j.example.httpjob _job) {
this.parent = parent;
this._job = _job;
}
b4j.example.main parent;
b4j.example.httpjob _job;
uk.co.gingerscrack.jSoup.jSoup _jsoup = null;
anywheresoftware.b4a.objects.collections.List _ls = null;
String _table = "";
anywheresoftware.b4a.objects.collections.List _pretopicslist = null;
int _i = 0;
String _tp = "";
int _a = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
String _thdate = "";
b4j.example.httpjob _j = null;
float _set = 0f;
anywheresoftware.b4a.objects.collections.List _l = null;
anywheresoftware.b4a.objects.collections.List _data = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
String _dat = "";
anywheresoftware.b4a.objects.collections.List _dl = null;
float _add = 0f;
String _ss = "";
double _r = 0;
String _addstr = "";
double _rs = 0;
anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator _jdlp = null;
anywheresoftware.b4a.objects.collections.Map _mm = null;
String _dlpp = "";
anywheresoftware.b4j.objects.collections.JSONParser _jsdlpp = null;
anywheresoftware.b4a.objects.collections.Map _mddlpp = null;
anywheresoftware.b4j.objects.collections.JSONParser _jsondlp = null;
anywheresoftware.b4a.objects.collections.List _lp = null;
anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator _rtjson = null;
String _presult = "";
anywheresoftware.b4a.objects.collections.Map _mp1 = null;
anywheresoftware.b4a.objects.collections.List _ids = null;
anywheresoftware.b4a.objects.collections.List _flist = null;
anywheresoftware.b4a.objects.collections.List _slist = null;
String _id = "";
String _nm = "";
String _ftext = "";
String _stext = "";
int _b = 0;
anywheresoftware.b4a.objects.collections.Map _postm = null;
anywheresoftware.b4a.objects.collections.Map _mdata = null;
anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator _finalpostjson = null;
int step15;
int limit15;
int step22;
int limit22;
anywheresoftware.b4a.BA.IterableList group138;
int index138;
int groupLen138;
int step142;
int limit142;
anywheresoftware.b4a.BA.IterableList group149;
int index149;
int groupLen149;
int step153;
int limit153;
int step161;
int limit161;
int step165;
int limit165;
anywheresoftware.b4a.BA.IterableList group180;
int index180;
int groupLen180;
int step184;
int limit184;
anywheresoftware.b4a.BA.IterableList group190;
int index190;
int groupLen190;
int step194;
int limit194;
int step202;
int limit202;
int step206;
int limit206;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 100;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 180;
this.catchState = 179;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 179;
 //BA.debugLineNum = 101;BA.debugLine="If job.Success Then";
if (true) break;

case 4:
//if
this.state = 177;
if (_job._success /*boolean*/ ) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 102;BA.debugLine="Select job.JobName";
if (true) break;

case 7:
//select
this.state = 176;
switch (BA.switchObjectToInt(_job._jobname /*String*/ ,"getter","datagetter","dlp","poststockdata")) {
case 0: {
this.state = 9;
if (true) break;
}
case 1: {
this.state = 28;
if (true) break;
}
case 2: {
this.state = 67;
if (true) break;
}
case 3: {
this.state = 175;
if (true) break;
}
}
if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 105;BA.debugLine="Sleep(3000)";
anywheresoftware.b4a.keywords.Common.Sleep(ba,this,(int) (3000));
this.state = 181;
return;
case 181:
//C
this.state = 10;
;
 //BA.debugLineNum = 106;BA.debugLine="timer.Enabled=True";
parent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 108;BA.debugLine="Dim jsoup As jSoup";
_jsoup = new uk.co.gingerscrack.jSoup.jSoup();
 //BA.debugLineNum = 110;BA.debugLine="Dim ls As List =jsoup.getElementsByTag(job.Ge";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = _jsoup.getElementsByTag(_job._getstring /*String*/ (),"tbody");
 //BA.debugLineNum = 111;BA.debugLine="If ls.Size>0 Then";
if (true) break;

case 10:
//if
this.state = 26;
if (_ls.getSize()>0) { 
this.state = 12;
}else {
this.state = 25;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 112;BA.debugLine="Dim table As String = ls.Get(1)";
_table = BA.ObjectToString(_ls.Get((int) (1)));
 //BA.debugLineNum = 114;BA.debugLine="dateList = jsoup.selectorElementText(table,\"";
parent._datelist = _jsoup.selectorElementText(_table,"span");
 //BA.debugLineNum = 115;BA.debugLine="Dim preTopicsList As List = jsoup.selectorEl";
_pretopicslist = new anywheresoftware.b4a.objects.collections.List();
_pretopicslist = _jsoup.selectorElementAttr(_table,"a","href");
 //BA.debugLineNum = 117;BA.debugLine="idlist.Initialize";
parent._idlist.Initialize();
 //BA.debugLineNum = 119;BA.debugLine="TopicsList.Initialize";
parent._topicslist.Initialize();
 //BA.debugLineNum = 120;BA.debugLine="For i = 0 To preTopicsList.Size -1";
if (true) break;

case 13:
//for
this.state = 20;
step15 = 1;
limit15 = (int) (_pretopicslist.getSize()-1);
_i = (int) (0) ;
this.state = 182;
if (true) break;

case 182:
//C
this.state = 20;
if ((step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15)) this.state = 15;
if (true) break;

case 183:
//C
this.state = 182;
_i = ((int)(0 + _i + step15)) ;
if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 121;BA.debugLine="Dim tp  As String = preTopicsList.Get(i)";
_tp = BA.ObjectToString(_pretopicslist.Get(_i));
 //BA.debugLineNum = 122;BA.debugLine="If tp.Contains(\"#\") = False And tp.Contains";
if (true) break;

case 16:
//if
this.state = 19;
if (_tp.contains("#")==anywheresoftware.b4a.keywords.Common.False && _tp.contains("topic")) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 123;BA.debugLine="idlist.Add(tp.SubString2(tp.IndexOf(\"=\")+1";
parent._idlist.Add((Object)(_tp.substring((int) (_tp.indexOf("=")+1),_tp.length()).replace(".0","")));
 //BA.debugLineNum = 124;BA.debugLine="TopicsList.Add(tp)";
parent._topicslist.Add((Object)(_tp));
 if (true) break;

case 19:
//C
this.state = 183;
;
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 128;BA.debugLine="For a = 0 To Min(TopicsList.Size - 1, dateLi";

case 20:
//for
this.state = 23;
step22 = 1;
limit22 = (int) (anywheresoftware.b4a.keywords.Common.Min(parent._topicslist.getSize()-1,parent._datelist.getSize()-1));
_a = (int) (0) ;
this.state = 184;
if (true) break;

case 184:
//C
this.state = 23;
if ((step22 > 0 && _a <= limit22) || (step22 < 0 && _a >= limit22)) this.state = 22;
if (true) break;

case 185:
//C
this.state = 184;
_a = ((int)(0 + _a + step22)) ;
if (true) break;

case 22:
//C
this.state = 185;
 //BA.debugLineNum = 129;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 130;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 132;BA.debugLine="Dim thdate As String = dateList.Get(a)";
_thdate = BA.ObjectToString(parent._datelist.Get(_a));
 //BA.debugLineNum = 133;BA.debugLine="thdate = thdate.Replace(\" (ช่อง 9)\", \"\")";
_thdate = _thdate.replace(" (ช่อง 9)","");
 //BA.debugLineNum = 134;BA.debugLine="m.Put(\"thaidate\", thdate)";
_m.Put((Object)("thaidate"),(Object)(_thdate));
 //BA.debugLineNum = 135;BA.debugLine="m.Put(\"date\", ThaiToEngDate(thdate))";
_m.Put((Object)("date"),(Object)(_thaitoengdate(_thdate)));
 //BA.debugLineNum = 136;BA.debugLine="m.Put(\"id\", idlist.Get(a))";
_m.Put((Object)("id"),parent._idlist.Get(_a));
 //BA.debugLineNum = 138;BA.debugLine="Log(m.Get(\"date\"))";
anywheresoftware.b4a.keywords.Common.LogImpl("2458791",BA.ObjectToString(_m.Get((Object)("date"))),0);
 //BA.debugLineNum = 139;BA.debugLine="downloaddata(TopicsList.Get(a), m)";
_downloaddata(BA.ObjectToString(parent._topicslist.Get(_a)),_m);
 //BA.debugLineNum = 144;BA.debugLine="Sleep(4000)";
anywheresoftware.b4a.keywords.Common.Sleep(ba,this,(int) (4000));
this.state = 186;
return;
case 186:
//C
this.state = 185;
;
 if (true) break;
if (true) break;

case 23:
//C
this.state = 26;
;
 if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 149;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 26:
//C
this.state = 176;
;
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 153;BA.debugLine="Dim j As HttpJob = Sender";
_j = (b4j.example.httpjob)(anywheresoftware.b4a.keywords.Common.Sender(ba));
 //BA.debugLineNum = 154;BA.debugLine="Dim m As Map = j.Tag";
_m = new anywheresoftware.b4a.objects.collections.Map();
_m = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_j._tag /*Object*/ ));
 //BA.debugLineNum = 155;BA.debugLine="Dim jsoup As jSoup";
_jsoup = new uk.co.gingerscrack.jSoup.jSoup();
 //BA.debugLineNum = 156;BA.debugLine="Dim ls As List = jsoup.getElementsByClass(job";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = _jsoup.getElementsByClass(_job._getstring /*String*/ (),"postarea");
 //BA.debugLineNum = 157;BA.debugLine="Dim set As Float";
_set = 0f;
 //BA.debugLineNum = 158;BA.debugLine="If ls.Size >0 Then";
if (true) break;

case 29:
//if
this.state = 65;
if (_ls.getSize()>0) { 
this.state = 31;
}if (true) break;

case 31:
//C
this.state = 32;
 //BA.debugLineNum = 159;BA.debugLine="Dim l As List";
_l = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 160;BA.debugLine="l.Initialize";
_l.Initialize();
 //BA.debugLineNum = 161;BA.debugLine="Dim data As List";
_data = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 162;BA.debugLine="data.Initialize";
_data.Initialize();
 //BA.debugLineNum = 163;BA.debugLine="Select ts.isMorning";
if (true) break;

case 32:
//select
this.state = 49;
switch (BA.switchObjectToInt(parent._ts.isMorning /*boolean*/ ,anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False)) {
case 0: {
this.state = 34;
if (true) break;
}
case 1: {
this.state = 42;
if (true) break;
}
}
if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 165;BA.debugLine="If ts.isOpen=True Then";
if (true) break;

case 35:
//if
this.state = 40;
if (parent._ts.isOpen /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 37;
}else {
this.state = 39;
}if (true) break;

case 37:
//C
this.state = 40;
 //BA.debugLineNum = 166;BA.debugLine="data = 	jsoup.selectorElementText(ls.Get(0";
_data = _jsoup.selectorElementText(BA.ObjectToString(_ls.Get((int) (0))),"span");
 if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 168;BA.debugLine="data = 	jsoup.selectorElementText(ls.Get(1";
_data = _jsoup.selectorElementText(BA.ObjectToString(_ls.Get((int) (1))),"span");
 if (true) break;

case 40:
//C
this.state = 49;
;
 if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 172;BA.debugLine="If ts.isOpen=True Then";
if (true) break;

case 43:
//if
this.state = 48;
if (parent._ts.isOpen /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 45;
}else {
this.state = 47;
}if (true) break;

case 45:
//C
this.state = 48;
 //BA.debugLineNum = 173;BA.debugLine="data = 	jsoup.selectorElementText(ls.Get(";
_data = _jsoup.selectorElementText(BA.ObjectToString(_ls.Get((int) (2))),"span");
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 175;BA.debugLine="data = 	jsoup.selectorElementText(ls.Get(";
_data = _jsoup.selectorElementText(BA.ObjectToString(_ls.Get((int) (3))),"span");
 if (true) break;

case 48:
//C
this.state = 49;
;
 if (true) break;

case 49:
//C
this.state = 50;
;
 //BA.debugLineNum = 180;BA.debugLine="Log(data.Size)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458833",BA.NumberToString(_data.getSize()),0);
 //BA.debugLineNum = 181;BA.debugLine="Log(\"===========\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458834","===========",0);
 //BA.debugLineNum = 183;BA.debugLine="Dim mp As Map";
_mp = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 184;BA.debugLine="mp.Initialize";
_mp.Initialize();
 //BA.debugLineNum = 185;BA.debugLine="If data.Size >0 Then";
if (true) break;

case 50:
//if
this.state = 64;
if (_data.getSize()>0) { 
this.state = 52;
}if (true) break;

case 52:
//C
this.state = 53;
 //BA.debugLineNum = 186;BA.debugLine="m.Put(\"title\",data.Get(0))";
_m.Put((Object)("title"),_data.Get((int) (0)));
 //BA.debugLineNum = 187;BA.debugLine="Dim dat As String = data.Get(2)";
_dat = BA.ObjectToString(_data.Get((int) (2)));
 //BA.debugLineNum = 188;BA.debugLine="Log(\"----------\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458841","----------",0);
 //BA.debugLineNum = 189;BA.debugLine="Dim dl As List =Regex.Split(\" \",dat)";
_dl = new anywheresoftware.b4a.objects.collections.List();
_dl = anywheresoftware.b4a.keywords.Common.ArrayToList(anywheresoftware.b4a.keywords.Common.Regex.Split(" ",_dat));
 //BA.debugLineNum = 190;BA.debugLine="set= dl.Get(0)";
_set = (float)(BA.ObjectToNumber(_dl.Get((int) (0))));
 //BA.debugLineNum = 193;BA.debugLine="Dim add As Float = dl.get(1) & dl.Get(2)";
_add = (float)(Double.parseDouble(BA.ObjectToString(_dl.Get((int) (1)))+BA.ObjectToString(_dl.Get((int) (2)))));
 //BA.debugLineNum = 194;BA.debugLine="Log(\"----\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458847","----",0);
 //BA.debugLineNum = 195;BA.debugLine="Log(\"xxxxxxxxxxx\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458848","xxxxxxxxxxx",0);
 //BA.debugLineNum = 197;BA.debugLine="Log(\"-----------\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458850","-----------",0);
 //BA.debugLineNum = 201;BA.debugLine="Log(\"xxxxxxxxxxxxxxxxxxx\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458854","xxxxxxxxxxxxxxxxxxx",0);
 //BA.debugLineNum = 202;BA.debugLine="Log(set)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458855",BA.NumberToString(_set),0);
 //BA.debugLineNum = 203;BA.debugLine="Dim ss As String = set";
_ss = BA.NumberToString(_set);
 //BA.debugLineNum = 205;BA.debugLine="If ss <> \"0000. 00\" Then";
if (true) break;

case 53:
//if
this.state = 63;
if ((_ss).equals("0000. 00") == false) { 
this.state = 55;
}if (true) break;

case 55:
//C
this.state = 56;
 //BA.debugLineNum = 206;BA.debugLine="Dim r As Double =ss.SubString2(ss.IndexOf(";
_r = (double)(Double.parseDouble(_ss.substring((int) (_ss.indexOf(".")-1),(int) (_ss.indexOf(".")+2))));
 //BA.debugLineNum = 207;BA.debugLine="Dim addstr As String = add";
_addstr = BA.NumberToString(_add);
 //BA.debugLineNum = 208;BA.debugLine="Dim rs As Double = addstr.SubString2(addst";
_rs = (double)(Double.parseDouble(_addstr.substring((int) (_addstr.indexOf(".")-1),(int) (_addstr.indexOf(".")+2))));
 //BA.debugLineNum = 209;BA.debugLine="m.Put(\"fnum\",Calculate.AdjustDouble(r))";
_m.Put((Object)("fnum"),(Object)(parent._calculate._adjustdouble /*String*/ (_r)));
 //BA.debugLineNum = 210;BA.debugLine="m.Put(\"snum\",Calculate.AdjustDouble(rs))";
_m.Put((Object)("snum"),(Object)(parent._calculate._adjustdouble /*String*/ (_rs)));
 //BA.debugLineNum = 211;BA.debugLine="m.Put(\"set\",set)";
_m.Put((Object)("set"),(Object)(_set));
 //BA.debugLineNum = 212;BA.debugLine="m.Put(\"change\",add)";
_m.Put((Object)("change"),(Object)(_add));
 //BA.debugLineNum = 213;BA.debugLine="Log(add)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458866",BA.NumberToString(_add),0);
 //BA.debugLineNum = 214;BA.debugLine="If ts.isMorning=True Then";
if (true) break;

case 56:
//if
this.state = 59;
if (parent._ts.isMorning /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 58;
}if (true) break;

case 58:
//C
this.state = 59;
 //BA.debugLineNum = 215;BA.debugLine="Log(\"morning\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458868","morning",0);
 //BA.debugLineNum = 216;BA.debugLine="setlottotime = DateTime.TimeParse(\"12:05:";
parent._setlottotime = anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("12:05:30");
 //BA.debugLineNum = 218;BA.debugLine="Dim jdlp As JSONGenerator";
_jdlp = new anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 219;BA.debugLine="jdlp.Initialize(m)";
_jdlp.Initialize(_m);
 //BA.debugLineNum = 220;BA.debugLine="Calculate.jobtag = jdlp.ToString";
parent._calculate._jobtag /*String*/  = _jdlp.ToString();
 //BA.debugLineNum = 221;BA.debugLine="dlp(\"https://shwemyanmar2d.us/?q=SELECT *";
_dlp("https://shwemyanmar2d.us/?q=SELECT * FROM dailyresults ORDER BY date DESC");
 if (true) break;
;
 //BA.debugLineNum = 224;BA.debugLine="If ts.isMorning=False Then";

case 59:
//if
this.state = 62;
if (parent._ts.isMorning /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 61;
}if (true) break;

case 61:
//C
this.state = 62;
 //BA.debugLineNum = 225;BA.debugLine="Log(\"evening\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458878","evening",0);
 //BA.debugLineNum = 227;BA.debugLine="setlottotime = DateTime.TimeParse(\"14:03:";
parent._setlottotime = anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("14:03:00");
 //BA.debugLineNum = 228;BA.debugLine="Dim mm As Map  = m";
_mm = new anywheresoftware.b4a.objects.collections.Map();
_mm = _m;
 //BA.debugLineNum = 229;BA.debugLine="Dim jdlp As JSONGenerator";
_jdlp = new anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 230;BA.debugLine="jdlp.Initialize(mm)";
_jdlp.Initialize(_mm);
 //BA.debugLineNum = 231;BA.debugLine="Calculate.jobtag = jdlp.ToString";
parent._calculate._jobtag /*String*/  = _jdlp.ToString();
 //BA.debugLineNum = 232;BA.debugLine="dlp(\"https://shwemyanmar2d.us/live.php\")";
_dlp("https://shwemyanmar2d.us/live.php");
 if (true) break;

case 62:
//C
this.state = 63;
;
 if (true) break;

case 63:
//C
this.state = 64;
;
 if (true) break;

case 64:
//C
this.state = 65;
;
 if (true) break;

case 65:
//C
this.state = 176;
;
 if (true) break;

case 67:
//C
this.state = 68;
 //BA.debugLineNum = 244;BA.debugLine="Log(\"dmp\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458897","dmp",0);
 //BA.debugLineNum = 245;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458898",_job._getstring /*String*/ (),0);
 //BA.debugLineNum = 247;BA.debugLine="Dim  dlpp As String = Calculate.jobtag";
_dlpp = parent._calculate._jobtag /*String*/ ;
 //BA.debugLineNum = 249;BA.debugLine="Dim jsdlpp As JSONParser";
_jsdlpp = new anywheresoftware.b4j.objects.collections.JSONParser();
 //BA.debugLineNum = 250;BA.debugLine="jsdlpp.Initialize(dlpp)";
_jsdlpp.Initialize(_dlpp);
 //BA.debugLineNum = 251;BA.debugLine="Dim mddlpp As Map = jsdlpp.NextObject";
_mddlpp = new anywheresoftware.b4a.objects.collections.Map();
_mddlpp = _jsdlpp.NextObject();
 //BA.debugLineNum = 253;BA.debugLine="Dim jsondlp As JSONParser";
_jsondlp = new anywheresoftware.b4j.objects.collections.JSONParser();
 //BA.debugLineNum = 254;BA.debugLine="jsondlp.Initialize(job.GetString)";
_jsondlp.Initialize(_job._getstring /*String*/ ());
 //BA.debugLineNum = 255;BA.debugLine="Log(\"=========\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458908","=========",0);
 //BA.debugLineNum = 256;BA.debugLine="Dim lp As List = jsondlp.NextArray";
_lp = new anywheresoftware.b4a.objects.collections.List();
_lp = _jsondlp.NextArray();
 //BA.debugLineNum = 257;BA.debugLine="If lp.Size >0 Then";
if (true) break;

case 68:
//if
this.state = 173;
if (_lp.getSize()>0) { 
this.state = 70;
}if (true) break;

case 70:
//C
this.state = 71;
 //BA.debugLineNum = 259;BA.debugLine="Dim rtjson As JSONGenerator";
_rtjson = new anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 261;BA.debugLine="Dim presult As String";
_presult = "";
 //BA.debugLineNum = 262;BA.debugLine="If ts.isMorning=True Then";
if (true) break;

case 71:
//if
this.state = 151;
if (parent._ts.isMorning /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 73;
}else {
this.state = 115;
}if (true) break;

case 73:
//C
this.state = 74;
 //BA.debugLineNum = 263;BA.debugLine="Log(\"==============\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458916","==============",0);
 //BA.debugLineNum = 264;BA.debugLine="Dim mp As Map = lp.Get(0)";
_mp = new anywheresoftware.b4a.objects.collections.Map();
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_lp.Get((int) (0))));
 //BA.debugLineNum = 265;BA.debugLine="presult= mp.Get(\"430\")";
_presult = BA.ObjectToString(_mp.Get((Object)("430")));
 //BA.debugLineNum = 266;BA.debugLine="If presult.Contains(\"--\") Then";
if (true) break;

case 74:
//if
this.state = 77;
if (_presult.contains("--")) { 
this.state = 76;
}if (true) break;

case 76:
//C
this.state = 77;
 //BA.debugLineNum = 267;BA.debugLine="Dim mp1  As Map = lp.Get(1)";
_mp1 = new anywheresoftware.b4a.objects.collections.Map();
_mp1 = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_lp.Get((int) (1))));
 //BA.debugLineNum = 268;BA.debugLine="presult =mp1. Get(\"430\")";
_presult = BA.ObjectToString(_mp1.Get((Object)("430")));
 if (true) break;

case 77:
//C
this.state = 78;
;
 //BA.debugLineNum = 271;BA.debugLine="Log(lp)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458924",BA.ObjectToString(_lp),0);
 //BA.debugLineNum = 272;BA.debugLine="Log(mp1)";
anywheresoftware.b4a.keywords.Common.LogImpl("2458925",BA.ObjectToString(_mp1),0);
 //BA.debugLineNum = 274;BA.debugLine="Log(\"><<><><>><><><><><><>><><><>\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458927","><<><><>><><><><><><>><><><>",0);
 //BA.debugLineNum = 277;BA.debugLine="Dim ids As List = Calculate.GenerateThreeGr";
_ids = new anywheresoftware.b4a.objects.collections.List();
_ids = parent._calculate._generatethreegradeidsfromsingledigit /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._digitsummod10 /*int*/ ((int)(Double.parseDouble(_presult))));
 //BA.debugLineNum = 279;BA.debugLine="Dim flist As List";
_flist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 280;BA.debugLine="flist.Initialize";
_flist.Initialize();
 //BA.debugLineNum = 281;BA.debugLine="Dim slist As List";
_slist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 282;BA.debugLine="slist.Initialize";
_slist.Initialize();
 //BA.debugLineNum = 284;BA.debugLine="For Each id As String In ids";
if (true) break;

case 78:
//for
this.state = 91;
group138 = _ids;
index138 = 0;
groupLen138 = group138.getSize();
this.state = 187;
if (true) break;

case 187:
//C
this.state = 91;
if (index138 < groupLen138) {
this.state = 80;
_id = BA.ObjectToString(group138.Get(index138));}
if (true) break;

case 188:
//C
this.state = 187;
index138++;
if (true) break;

case 80:
//C
this.state = 81;
 //BA.debugLineNum = 287;BA.debugLine="If id.Contains(Calculate.MostFrequentDigit";
if (true) break;

case 81:
//if
this.state = 90;
if (_id.contains(BA.NumberToString(parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("fnum"))))))) { 
this.state = 83;
}else {
this.state = 85;
}if (true) break;

case 83:
//C
this.state = 90;
 if (true) break;

case 85:
//C
this.state = 86;
 //BA.debugLineNum = 289;BA.debugLine="Dim ls As List = Calculate.CombineDigitWi";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = parent._calculate._combinedigitwithlist /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("fnum")))),_id);
 //BA.debugLineNum = 290;BA.debugLine="For i = 0 To ls.Size -1";
if (true) break;

case 86:
//for
this.state = 89;
step142 = 1;
limit142 = (int) (_ls.getSize()-1);
_i = (int) (0) ;
this.state = 189;
if (true) break;

case 189:
//C
this.state = 89;
if ((step142 > 0 && _i <= limit142) || (step142 < 0 && _i >= limit142)) this.state = 88;
if (true) break;

case 190:
//C
this.state = 189;
_i = ((int)(0 + _i + step142)) ;
if (true) break;

case 88:
//C
this.state = 190;
 //BA.debugLineNum = 291;BA.debugLine="Dim nm As String = ls.Get(i)";
_nm = BA.ObjectToString(_ls.Get(_i));
 //BA.debugLineNum = 293;BA.debugLine="flist.Add(nm)";
_flist.Add((Object)(_nm));
 if (true) break;
if (true) break;

case 89:
//C
this.state = 90;
;
 if (true) break;

case 90:
//C
this.state = 188;
;
 if (true) break;
if (true) break;

case 91:
//C
this.state = 92;
;
 //BA.debugLineNum = 299;BA.debugLine="Log(\"xxxxxxxxxxxxxx============\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2458952","xxxxxxxxxxxxxx============",0);
 //BA.debugLineNum = 301;BA.debugLine="For Each id As String In ids";
if (true) break;

case 92:
//for
this.state = 105;
group149 = _ids;
index149 = 0;
groupLen149 = group149.getSize();
this.state = 191;
if (true) break;

case 191:
//C
this.state = 105;
if (index149 < groupLen149) {
this.state = 94;
_id = BA.ObjectToString(group149.Get(index149));}
if (true) break;

case 192:
//C
this.state = 191;
index149++;
if (true) break;

case 94:
//C
this.state = 95;
 //BA.debugLineNum = 302;BA.debugLine="If id.Contains(Calculate.MostFrequentDigit";
if (true) break;

case 95:
//if
this.state = 104;
if (_id.contains(BA.NumberToString(parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("snum"))))))) { 
this.state = 97;
}else {
this.state = 99;
}if (true) break;

case 97:
//C
this.state = 104;
 if (true) break;

case 99:
//C
this.state = 100;
 //BA.debugLineNum = 304;BA.debugLine="Dim ls As List = Calculate.CombineDigitWi";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = parent._calculate._combinedigitwithlist /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("snum")))),_id);
 //BA.debugLineNum = 305;BA.debugLine="For i = 0 To ls.Size -1";
if (true) break;

case 100:
//for
this.state = 103;
step153 = 1;
limit153 = (int) (_ls.getSize()-1);
_i = (int) (0) ;
this.state = 193;
if (true) break;

case 193:
//C
this.state = 103;
if ((step153 > 0 && _i <= limit153) || (step153 < 0 && _i >= limit153)) this.state = 102;
if (true) break;

case 194:
//C
this.state = 193;
_i = ((int)(0 + _i + step153)) ;
if (true) break;

case 102:
//C
this.state = 194;
 //BA.debugLineNum = 306;BA.debugLine="Dim nm As String = ls.Get(i)";
_nm = BA.ObjectToString(_ls.Get(_i));
 //BA.debugLineNum = 308;BA.debugLine="slist.Add(nm)";
_slist.Add((Object)(_nm));
 if (true) break;
if (true) break;

case 103:
//C
this.state = 104;
;
 if (true) break;

case 104:
//C
this.state = 192;
;
 if (true) break;
if (true) break;

case 105:
//C
this.state = 106;
;
 //BA.debugLineNum = 313;BA.debugLine="Dim ftext As String";
_ftext = "";
 //BA.debugLineNum = 314;BA.debugLine="Dim stext As String";
_stext = "";
 //BA.debugLineNum = 316;BA.debugLine="For b = 0 To flist.Size -1";
if (true) break;

case 106:
//for
this.state = 109;
step161 = 1;
limit161 = (int) (_flist.getSize()-1);
_b = (int) (0) ;
this.state = 195;
if (true) break;

case 195:
//C
this.state = 109;
if ((step161 > 0 && _b <= limit161) || (step161 < 0 && _b >= limit161)) this.state = 108;
if (true) break;

case 196:
//C
this.state = 195;
_b = ((int)(0 + _b + step161)) ;
if (true) break;

case 108:
//C
this.state = 196;
 //BA.debugLineNum = 317;BA.debugLine="ftext =ftext & \"   \"& \"++\"&flist.Get(b)&\"";
_ftext = _ftext+"   "+"++"+BA.ObjectToString(_flist.Get(_b))+" ";
 if (true) break;
if (true) break;

case 109:
//C
this.state = 110;
;
 //BA.debugLineNum = 319;BA.debugLine="ftext = \"<b>\"& ftext &\"</b><br><br>\"";
_ftext = "<b>"+_ftext+"</b><br><br>";
 //BA.debugLineNum = 320;BA.debugLine="For a = 0 To slist.Size -1";
if (true) break;

case 110:
//for
this.state = 113;
step165 = 1;
limit165 = (int) (_slist.getSize()-1);
_a = (int) (0) ;
this.state = 197;
if (true) break;

case 197:
//C
this.state = 113;
if ((step165 > 0 && _a <= limit165) || (step165 < 0 && _a >= limit165)) this.state = 112;
if (true) break;

case 198:
//C
this.state = 197;
_a = ((int)(0 + _a + step165)) ;
if (true) break;

case 112:
//C
this.state = 198;
 //BA.debugLineNum = 321;BA.debugLine="stext =stext & \"   \"& \"++\"&slist.Get(a) &\"";
_stext = _stext+"   "+"++"+BA.ObjectToString(_slist.Get(_a))+" ";
 if (true) break;
if (true) break;

case 113:
//C
this.state = 151;
;
 //BA.debugLineNum = 323;BA.debugLine="stext = \"<b>\"& stext &\"</b><br>\"";
_stext = "<b>"+_stext+"</b><br>";
 //BA.debugLineNum = 324;BA.debugLine="mddlpp.Put(\"text1\",ftext)";
_mddlpp.Put((Object)("text1"),(Object)(_ftext));
 //BA.debugLineNum = 325;BA.debugLine="mddlpp.Put(\"text2\",stext)";
_mddlpp.Put((Object)("text2"),(Object)(_stext));
 //BA.debugLineNum = 327;BA.debugLine="rtjson.Initialize(mddlpp)";
_rtjson.Initialize(_mddlpp);
 if (true) break;

case 115:
//C
this.state = 116;
 //BA.debugLineNum = 334;BA.debugLine="Dim mp As Map = lp.Get(0)";
_mp = new anywheresoftware.b4a.objects.collections.Map();
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(_lp.Get((int) (0))));
 //BA.debugLineNum = 335;BA.debugLine="presult= mp.Get(\"1200\")";
_presult = BA.ObjectToString(_mp.Get((Object)("1200")));
 //BA.debugLineNum = 336;BA.debugLine="Dim ids As List = Calculate.GenerateThreeGr";
_ids = new anywheresoftware.b4a.objects.collections.List();
_ids = parent._calculate._generatethreegradeidsfromsingledigit /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._digitsummod10 /*int*/ ((int)(Double.parseDouble(_presult))));
 //BA.debugLineNum = 337;BA.debugLine="Dim flist As List";
_flist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 338;BA.debugLine="flist.Initialize";
_flist.Initialize();
 //BA.debugLineNum = 339;BA.debugLine="Dim slist As List";
_slist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 340;BA.debugLine="slist.Initialize";
_slist.Initialize();
 //BA.debugLineNum = 341;BA.debugLine="For Each id As String In ids";
if (true) break;

case 116:
//for
this.state = 129;
group180 = _ids;
index180 = 0;
groupLen180 = group180.getSize();
this.state = 199;
if (true) break;

case 199:
//C
this.state = 129;
if (index180 < groupLen180) {
this.state = 118;
_id = BA.ObjectToString(group180.Get(index180));}
if (true) break;

case 200:
//C
this.state = 199;
index180++;
if (true) break;

case 118:
//C
this.state = 119;
 //BA.debugLineNum = 344;BA.debugLine="If id.Contains(Calculate.MostFrequentDigit";
if (true) break;

case 119:
//if
this.state = 128;
if (_id.contains(BA.NumberToString(parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("fnum"))))))) { 
this.state = 121;
}else {
this.state = 123;
}if (true) break;

case 121:
//C
this.state = 128;
 if (true) break;

case 123:
//C
this.state = 124;
 //BA.debugLineNum = 346;BA.debugLine="Dim ls As List = Calculate.CombineDigitWi";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = parent._calculate._combinedigitwithlist /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("fnum")))),_id);
 //BA.debugLineNum = 347;BA.debugLine="For i = 0 To ls.Size -1";
if (true) break;

case 124:
//for
this.state = 127;
step184 = 1;
limit184 = (int) (_ls.getSize()-1);
_i = (int) (0) ;
this.state = 201;
if (true) break;

case 201:
//C
this.state = 127;
if ((step184 > 0 && _i <= limit184) || (step184 < 0 && _i >= limit184)) this.state = 126;
if (true) break;

case 202:
//C
this.state = 201;
_i = ((int)(0 + _i + step184)) ;
if (true) break;

case 126:
//C
this.state = 202;
 //BA.debugLineNum = 348;BA.debugLine="Dim nm As String = ls.Get(i)";
_nm = BA.ObjectToString(_ls.Get(_i));
 //BA.debugLineNum = 350;BA.debugLine="flist.Add(nm)";
_flist.Add((Object)(_nm));
 if (true) break;
if (true) break;

case 127:
//C
this.state = 128;
;
 if (true) break;

case 128:
//C
this.state = 200;
;
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 357;BA.debugLine="For Each id As String In ids";

case 129:
//for
this.state = 142;
group190 = _ids;
index190 = 0;
groupLen190 = group190.getSize();
this.state = 203;
if (true) break;

case 203:
//C
this.state = 142;
if (index190 < groupLen190) {
this.state = 131;
_id = BA.ObjectToString(group190.Get(index190));}
if (true) break;

case 204:
//C
this.state = 203;
index190++;
if (true) break;

case 131:
//C
this.state = 132;
 //BA.debugLineNum = 359;BA.debugLine="If id.Contains(Calculate.MostFrequentDigit";
if (true) break;

case 132:
//if
this.state = 141;
if (_id.contains(BA.NumberToString(parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("snum"))))))) { 
this.state = 134;
}else {
this.state = 136;
}if (true) break;

case 134:
//C
this.state = 141;
 if (true) break;

case 136:
//C
this.state = 137;
 //BA.debugLineNum = 361;BA.debugLine="Dim ls As List = Calculate.CombineDigitWi";
_ls = new anywheresoftware.b4a.objects.collections.List();
_ls = parent._calculate._combinedigitwithlist /*anywheresoftware.b4a.objects.collections.List*/ (parent._calculate._mostfrequentdigit /*int*/ (BA.ObjectToString(_mddlpp.Get((Object)("snum")))),_id);
 //BA.debugLineNum = 362;BA.debugLine="For i = 0 To ls.Size -1";
if (true) break;

case 137:
//for
this.state = 140;
step194 = 1;
limit194 = (int) (_ls.getSize()-1);
_i = (int) (0) ;
this.state = 205;
if (true) break;

case 205:
//C
this.state = 140;
if ((step194 > 0 && _i <= limit194) || (step194 < 0 && _i >= limit194)) this.state = 139;
if (true) break;

case 206:
//C
this.state = 205;
_i = ((int)(0 + _i + step194)) ;
if (true) break;

case 139:
//C
this.state = 206;
 //BA.debugLineNum = 363;BA.debugLine="Dim nm As String = ls.Get(i)";
_nm = BA.ObjectToString(_ls.Get(_i));
 //BA.debugLineNum = 365;BA.debugLine="slist.Add(nm)";
_slist.Add((Object)(_nm));
 if (true) break;
if (true) break;

case 140:
//C
this.state = 141;
;
 if (true) break;

case 141:
//C
this.state = 204;
;
 if (true) break;
if (true) break;

case 142:
//C
this.state = 143;
;
 //BA.debugLineNum = 371;BA.debugLine="Dim ftext As String";
_ftext = "";
 //BA.debugLineNum = 372;BA.debugLine="Dim stext As String";
_stext = "";
 //BA.debugLineNum = 374;BA.debugLine="For b = 0 To flist.Size -1";
if (true) break;

case 143:
//for
this.state = 146;
step202 = 1;
limit202 = (int) (_flist.getSize()-1);
_b = (int) (0) ;
this.state = 207;
if (true) break;

case 207:
//C
this.state = 146;
if ((step202 > 0 && _b <= limit202) || (step202 < 0 && _b >= limit202)) this.state = 145;
if (true) break;

case 208:
//C
this.state = 207;
_b = ((int)(0 + _b + step202)) ;
if (true) break;

case 145:
//C
this.state = 208;
 //BA.debugLineNum = 375;BA.debugLine="ftext =ftext & \" \"& \"++\"&flist.Get(b)&\" \"";
_ftext = _ftext+" "+"++"+BA.ObjectToString(_flist.Get(_b))+" ";
 if (true) break;
if (true) break;

case 146:
//C
this.state = 147;
;
 //BA.debugLineNum = 377;BA.debugLine="ftext = \"<b>\"& ftext &\"</b><br><br>\"";
_ftext = "<b>"+_ftext+"</b><br><br>";
 //BA.debugLineNum = 378;BA.debugLine="For a = 0 To slist.Size -1";
if (true) break;

case 147:
//for
this.state = 150;
step206 = 1;
limit206 = (int) (_slist.getSize()-1);
_a = (int) (0) ;
this.state = 209;
if (true) break;

case 209:
//C
this.state = 150;
if ((step206 > 0 && _a <= limit206) || (step206 < 0 && _a >= limit206)) this.state = 149;
if (true) break;

case 210:
//C
this.state = 209;
_a = ((int)(0 + _a + step206)) ;
if (true) break;

case 149:
//C
this.state = 210;
 //BA.debugLineNum = 379;BA.debugLine="Log(stext)";
anywheresoftware.b4a.keywords.Common.LogImpl("2459032",_stext,0);
 //BA.debugLineNum = 380;BA.debugLine="stext =stext & \" \"& \"++\"&slist.Get(a) &\" \"";
_stext = _stext+" "+"++"+BA.ObjectToString(_slist.Get(_a))+" ";
 if (true) break;
if (true) break;

case 150:
//C
this.state = 151;
;
 //BA.debugLineNum = 382;BA.debugLine="stext = \"<b>\"& stext &\"</b><br>\"";
_stext = "<b>"+_stext+"</b><br>";
 //BA.debugLineNum = 383;BA.debugLine="mddlpp.Put(\"text1\",ftext)";
_mddlpp.Put((Object)("text1"),(Object)(_ftext));
 //BA.debugLineNum = 384;BA.debugLine="mddlpp.Put(\"text2\",stext)";
_mddlpp.Put((Object)("text2"),(Object)(_stext));
 //BA.debugLineNum = 386;BA.debugLine="rtjson.Initialize(mddlpp)";
_rtjson.Initialize(_mddlpp);
 if (true) break;

case 151:
//C
this.state = 152;
;
 //BA.debugLineNum = 395;BA.debugLine="Dim postm As Map";
_postm = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 396;BA.debugLine="postm.Initialize";
_postm.Initialize();
 //BA.debugLineNum = 397;BA.debugLine="postm.Put(\"date\",mddlpp.Get(\"date\"))";
_postm.Put((Object)("date"),_mddlpp.Get((Object)("date")));
 //BA.debugLineNum = 398;BA.debugLine="Dim mdata As Map";
_mdata = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 399;BA.debugLine="mdata.Initialize";
_mdata.Initialize();
 //BA.debugLineNum = 400;BA.debugLine="mdata.Put(\"index\",mddlpp.Get(\"set\"))";
_mdata.Put((Object)("index"),_mddlpp.Get((Object)("set")));
 //BA.debugLineNum = 401;BA.debugLine="mdata.Put(\"change\",mddlpp.Get(\"change\"))";
_mdata.Put((Object)("change"),_mddlpp.Get((Object)("change")));
 //BA.debugLineNum = 402;BA.debugLine="If ts.isOpen= True Then";
if (true) break;

case 152:
//if
this.state = 155;
if (parent._ts.isOpen /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 154;
}if (true) break;

case 154:
//C
this.state = 155;
 //BA.debugLineNum = 403;BA.debugLine="mdata.Put(\"highlights\",mddlpp.Get(\"text1\")&";
_mdata.Put((Object)("highlights"),(Object)(BA.ObjectToString(_mddlpp.Get((Object)("text1")))+BA.ObjectToString(_mddlpp.Get((Object)("text2")))));
 if (true) break;
;
 //BA.debugLineNum = 406;BA.debugLine="Select ts.isMorning";

case 155:
//select
this.state = 172;
switch (BA.switchObjectToInt(parent._ts.isMorning /*boolean*/ ,anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False)) {
case 0: {
this.state = 157;
if (true) break;
}
case 1: {
this.state = 165;
if (true) break;
}
}
if (true) break;

case 157:
//C
this.state = 158;
 //BA.debugLineNum = 408;BA.debugLine="If ts.isOpen = True Then";
if (true) break;

case 158:
//if
this.state = 163;
if (parent._ts.isOpen /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 160;
}else {
this.state = 162;
}if (true) break;

case 160:
//C
this.state = 163;
 //BA.debugLineNum = 409;BA.debugLine="postm.Put(\"morning_open\",mdata)";
_postm.Put((Object)("morning_open"),(Object)(_mdata.getObject()));
 if (true) break;

case 162:
//C
this.state = 163;
 //BA.debugLineNum = 411;BA.debugLine="postm.Put(\"morning_close\",mdata)";
_postm.Put((Object)("morning_close"),(Object)(_mdata.getObject()));
 if (true) break;

case 163:
//C
this.state = 172;
;
 if (true) break;

case 165:
//C
this.state = 166;
 //BA.debugLineNum = 414;BA.debugLine="If ts.isOpen = True Then";
if (true) break;

case 166:
//if
this.state = 171;
if (parent._ts.isOpen /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 168;
}else {
this.state = 170;
}if (true) break;

case 168:
//C
this.state = 171;
 //BA.debugLineNum = 415;BA.debugLine="postm.Put(\"afternoon_open\",mdata)";
_postm.Put((Object)("afternoon_open"),(Object)(_mdata.getObject()));
 if (true) break;

case 170:
//C
this.state = 171;
 //BA.debugLineNum = 417;BA.debugLine="postm.Put(\"afternoon_close\",mdata)";
_postm.Put((Object)("afternoon_close"),(Object)(_mdata.getObject()));
 if (true) break;

case 171:
//C
this.state = 172;
;
 if (true) break;

case 172:
//C
this.state = 173;
;
 //BA.debugLineNum = 421;BA.debugLine="Dim finalpostjson As JSONGenerator";
_finalpostjson = new anywheresoftware.b4j.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 422;BA.debugLine="finalpostjson.Initialize(postm)";
_finalpostjson.Initialize(_postm);
 //BA.debugLineNum = 423;BA.debugLine="Log(finalpostjson.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("2459076",_finalpostjson.ToString(),0);
 //BA.debugLineNum = 424;BA.debugLine="PoststockData(finalpostjson.ToString)";
_poststockdata(_finalpostjson.ToString());
 if (true) break;

case 173:
//C
this.state = 176;
;
 if (true) break;

case 175:
//C
this.state = 176;
 //BA.debugLineNum = 427;BA.debugLine="timer.Enabled=True";
parent._timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 429;BA.debugLine="Log(job.GetString)";
anywheresoftware.b4a.keywords.Common.LogImpl("2459082",_job._getstring /*String*/ (),0);
 if (true) break;

case 176:
//C
this.state = 177;
;
 if (true) break;

case 177:
//C
this.state = 180;
;
 if (true) break;

case 179:
//C
this.state = 180;
this.catchState = 0;
 //BA.debugLineNum = 433;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("2459086",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(ba)),0);
 if (true) break;
if (true) break;

case 180:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
ba.setLastException(e0);}
            }
        }
    }
}
public static String  _poststockdata(String _data) throws Exception{
b4j.example.httpjob _j = null;
 //BA.debugLineNum = 73;BA.debugLine="Sub PoststockData (data As String)";
 //BA.debugLineNum = 74;BA.debugLine="Dim j As HttpJob";
_j = new b4j.example.httpjob();
 //BA.debugLineNum = 75;BA.debugLine="j.Initialize(\"poststockdata\",Me)";
_j._initialize /*String*/ (ba,"poststockdata",main.getObject());
 //BA.debugLineNum = 76;BA.debugLine="j.PostString(stockserver&\"api/market-data-analysi";
_j._poststring /*String*/ (_stockserver+"api/market-data-analysis",_data);
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}

private static boolean processGlobalsRun;
public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
calculate._process_globals();
httputils2service._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim jdata As String";
_jdata = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim livetimer As Timer";
_livetimer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim server As String = \"http://app.shwemyanmar2d.";
_server = "http://app.shwemyanmar2d.us/";
 //BA.debugLineNum = 11;BA.debugLine="Dim lserver  As String = \"http://localhost:1411\"";
_lserver = "http://localhost:1411";
 //BA.debugLineNum = 12;BA.debugLine="Dim site As String = \"https://www.lottosociety.co";
_site = "https://www.lottosociety.com/index.php?board=4.0";
 //BA.debugLineNum = 13;BA.debugLine="Dim dateList As List";
_datelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 14;BA.debugLine="Dim idlist As List";
_idlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 15;BA.debugLine="Dim TopicsList As List";
_topicslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 16;BA.debugLine="Dim mainlist As List";
_mainlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 17;BA.debugLine="Dim setlotto As String";
_setlotto = "";
 //BA.debugLineNum = 18;BA.debugLine="Dim setlottotime As Long";
_setlottotime = 0L;
 //BA.debugLineNum = 19;BA.debugLine="Type tiemandset(isOpen As Boolean,isMorning As Bo";
;
 //BA.debugLineNum = 20;BA.debugLine="Dim ts As tiemandset";
_ts = new b4j.example.main._tiemandset();
 //BA.debugLineNum = 21;BA.debugLine="Dim stockserver As String = \"https://thaistockana";
_stockserver = "https://thaistockanalysis.com/";
 //BA.debugLineNum = 23;BA.debugLine="Dim timer As Timer";
_timer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _selectparttime(long _dt) throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub selectParttime(dt As Long)";
 //BA.debugLineNum = 39;BA.debugLine="Log(\"check time\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131073","check time",0);
 //BA.debugLineNum = 40;BA.debugLine="If dt > DateTime.TimeParse(\"9:32:00\") And dt < Da";
if (_dt>anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("9:32:00") && _dt<anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("9:32:03")) { 
 //BA.debugLineNum = 41;BA.debugLine="ts.isMorning=True";
_ts.isMorning /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 42;BA.debugLine="ts.isOpen = True";
_ts.isOpen /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 43;BA.debugLine="download";
_download();
 //BA.debugLineNum = 44;BA.debugLine="Log(\"morning Open\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131078","morning Open",0);
 //BA.debugLineNum = 45;BA.debugLine="timer.Enabled=False";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else if(_dt>anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("12:03:00") && _dt<anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("12:03:03")) { 
 //BA.debugLineNum = 47;BA.debugLine="Log(\"morning Close\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131081","morning Close",0);
 //BA.debugLineNum = 48;BA.debugLine="Log(\"mN Close\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131082","mN Close",0);
 //BA.debugLineNum = 49;BA.debugLine="ts.isMorning=True";
_ts.isMorning /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 50;BA.debugLine="ts.isOpen=False";
_ts.isOpen /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 52;BA.debugLine="download";
_download();
 //BA.debugLineNum = 53;BA.debugLine="timer.Enabled=False";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else if(_dt>anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("13:33:00") && _dt<anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("13:33:03")) { 
 //BA.debugLineNum = 56;BA.debugLine="ts.isMorning=False";
_ts.isMorning /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 57;BA.debugLine="ts.isOpen=True";
_ts.isOpen /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 58;BA.debugLine="download";
_download();
 //BA.debugLineNum = 59;BA.debugLine="Log(\"Afternoon_Open\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131093","Afternoon_Open",0);
 //BA.debugLineNum = 61;BA.debugLine="timer.Enabled =False";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else if(_dt>anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("16:13:00") && _dt<anywheresoftware.b4a.keywords.Common.DateTime.TimeParse("16:13:03")) { 
 //BA.debugLineNum = 64;BA.debugLine="ts.isMorning=False";
_ts.isMorning /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 65;BA.debugLine="ts.isOpen=False";
_ts.isOpen /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 66;BA.debugLine="download";
_download();
 //BA.debugLineNum = 67;BA.debugLine="Log(\"Afternoon Close\")";
anywheresoftware.b4a.keywords.Common.LogImpl("2131101","Afternoon Close",0);
 //BA.debugLineNum = 68;BA.debugLine="timer.Enabled=False";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _thaitoengdate(String _thaitext) throws Exception{
anywheresoftware.b4a.objects.collections.Map _months = null;
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _day = 0;
String _monthname = "";
int _yearthai = 0;
int _month = 0;
int _year = 0;
 //BA.debugLineNum = 438;BA.debugLine="Sub ThaiToEngDate(thaiText As String) As String";
 //BA.debugLineNum = 439;BA.debugLine="Dim months As Map";
_months = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 440;BA.debugLine="months.Initialize";
_months.Initialize();
 //BA.debugLineNum = 441;BA.debugLine="months.Put(\"มกราคม\", 1)";
_months.Put((Object)("มกราคม"),(Object)(1));
 //BA.debugLineNum = 442;BA.debugLine="months.Put(\"กุมภาพันธ์\", 2)";
_months.Put((Object)("กุมภาพันธ์"),(Object)(2));
 //BA.debugLineNum = 443;BA.debugLine="months.Put(\"มีนาคม\", 3)";
_months.Put((Object)("มีนาคม"),(Object)(3));
 //BA.debugLineNum = 444;BA.debugLine="months.Put(\"เมษายน\", 4)";
_months.Put((Object)("เมษายน"),(Object)(4));
 //BA.debugLineNum = 445;BA.debugLine="months.Put(\"พฤษภาคม\", 5)";
_months.Put((Object)("พฤษภาคม"),(Object)(5));
 //BA.debugLineNum = 446;BA.debugLine="months.Put(\"มิถุนายน\", 6)";
_months.Put((Object)("มิถุนายน"),(Object)(6));
 //BA.debugLineNum = 447;BA.debugLine="months.Put(\"กรกฎาคม\", 7)";
_months.Put((Object)("กรกฎาคม"),(Object)(7));
 //BA.debugLineNum = 448;BA.debugLine="months.Put(\"สิงหาคม\", 8)";
_months.Put((Object)("สิงหาคม"),(Object)(8));
 //BA.debugLineNum = 449;BA.debugLine="months.Put(\"กันยายน\", 9)";
_months.Put((Object)("กันยายน"),(Object)(9));
 //BA.debugLineNum = 450;BA.debugLine="months.Put(\"ตุลาคม\", 10)";
_months.Put((Object)("ตุลาคม"),(Object)(10));
 //BA.debugLineNum = 451;BA.debugLine="months.Put(\"พฤศจิกายน\", 11)";
_months.Put((Object)("พฤศจิกายน"),(Object)(11));
 //BA.debugLineNum = 452;BA.debugLine="months.Put(\"ธันวาคม\", 12)";
_months.Put((Object)("ธันวาคม"),(Object)(12));
 //BA.debugLineNum = 454;BA.debugLine="thaiText = thaiText.Replace(\"กรกฏาคม\", \"กรกฎาคม\")";
_thaitext = _thaitext.replace("กรกฏาคม","กรกฎาคม");
 //BA.debugLineNum = 455;BA.debugLine="thaiText = thaiText.Replace(\"พฤศจกายน\", \"พฤศจิกาย";
_thaitext = _thaitext.replace("พฤศจกายน","พฤศจิกายน");
 //BA.debugLineNum = 456;BA.debugLine="thaiText = thaiText.Replace(\"เมษายนยน\", \"เมษายน\")";
_thaitext = _thaitext.replace("เมษายนยน","เมษายน");
 //BA.debugLineNum = 458;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"(\\d{1,2})\\s([ก-";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("(\\d{1,2})\\s([ก-๙]+)\\s(\\d{4})",_thaitext);
 //BA.debugLineNum = 459;BA.debugLine="If m.Find Then";
if (_m.Find()) { 
 //BA.debugLineNum = 460;BA.debugLine="Dim day As Int = m.Group(1)";
_day = (int)(Double.parseDouble(_m.Group((int) (1))));
 //BA.debugLineNum = 461;BA.debugLine="Dim monthName As String = m.Group(2)";
_monthname = _m.Group((int) (2));
 //BA.debugLineNum = 462;BA.debugLine="Dim yearThai As Int = m.Group(3)";
_yearthai = (int)(Double.parseDouble(_m.Group((int) (3))));
 //BA.debugLineNum = 464;BA.debugLine="If months.ContainsKey(monthName) Then";
if (_months.ContainsKey((Object)(_monthname))) { 
 //BA.debugLineNum = 465;BA.debugLine="Dim month As Int = months.Get(monthName)";
_month = (int)(BA.ObjectToNumber(_months.Get((Object)(_monthname))));
 //BA.debugLineNum = 466;BA.debugLine="Dim year As Int = yearThai - 543 ' Thai year to";
_year = (int) (_yearthai-543);
 //BA.debugLineNum = 469;BA.debugLine="Return NumberFormat2(year, 1, 0, 0, False) & \"-";
if (true) return anywheresoftware.b4a.keywords.Common.NumberFormat2(_year,(int) (1),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.False)+"-"+anywheresoftware.b4a.keywords.Common.NumberFormat2(_month,(int) (2),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.False)+"-"+anywheresoftware.b4a.keywords.Common.NumberFormat2(_day,(int) (2),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 475;BA.debugLine="Return \"Invalid Date\"";
if (true) return "Invalid Date";
 //BA.debugLineNum = 476;BA.debugLine="End Sub";
return "";
}
public static String  _timer_tick() throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub timer_tick";
 //BA.debugLineNum = 81;BA.debugLine="selectParttime(DateTime.Now)";
_selectparttime(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
}
