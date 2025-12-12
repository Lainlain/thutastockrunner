package b4j.example;


import anywheresoftware.b4a.BA;

public class calculate extends Object{
public static calculate mostCurrent = new calculate();

public static BA ba;
static {
		ba = new  anywheresoftware.b4a.StandardBA("b4j.example", "b4j.example.calculate", null);
		ba.loadHtSubs(calculate.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "b4j.example.calculate", ba);
		}
	}
    public static Class<?> getObject() {
		return calculate.class;
	}

 public static anywheresoftware.b4a.keywords.Common __c = null;
public static String _jobtag = "";
public static b4j.example.main _main = null;
public static b4j.example.httputils2service _httputils2service = null;
public static int  _add7andreturnlastdigit(int _n) throws Exception{
int _sum = 0;
String _s = "";
int _lastdigit = 0;
 //BA.debugLineNum = 9;BA.debugLine="Sub Add7AndReturnLastDigit(n As Int) As Int";
 //BA.debugLineNum = 10;BA.debugLine="Dim sum As Int = n + 7";
_sum = (int) (_n+7);
 //BA.debugLineNum = 11;BA.debugLine="Dim s As String = sum";
_s = BA.NumberToString(_sum);
 //BA.debugLineNum = 12;BA.debugLine="Dim lastDigit As Int = Asc(s.CharAt(s.Length - 1)";
_lastdigit = (int) (anywheresoftware.b4a.keywords.Common.Asc(_s.charAt((int) (_s.length()-1)))-48);
 //BA.debugLineNum = 13;BA.debugLine="Return lastDigit";
if (true) return _lastdigit;
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return 0;
}
public static String  _adjustdouble(double _d) throws Exception{
int _intpart = 0;
double _decimalpart = 0;
 //BA.debugLineNum = 92;BA.debugLine="Sub AdjustDouble(d As Double) As String";
 //BA.debugLineNum = 93;BA.debugLine="Dim intPart As Int = Floor(d)";
_intpart = (int) (anywheresoftware.b4a.keywords.Common.Floor(_d));
 //BA.debugLineNum = 94;BA.debugLine="Dim decimalPart As Double = d - intPart";
_decimalpart = _d-_intpart;
 //BA.debugLineNum = 96;BA.debugLine="If decimalPart * 10 > 5 Then";
if (_decimalpart*10>5) { 
 //BA.debugLineNum = 97;BA.debugLine="intPart = intPart + 1";
_intpart = (int) (_intpart+1);
 };
 //BA.debugLineNum = 99;BA.debugLine="If intPart =10 Then";
if (_intpart==10) { 
 //BA.debugLineNum = 100;BA.debugLine="intPart = \"0\"";
_intpart = (int)(Double.parseDouble("0"));
 };
 //BA.debugLineNum = 102;BA.debugLine="Return intPart";
if (true) return BA.NumberToString(_intpart);
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _combinedigitwithlist(int _digit,String _existdigits) throws Exception{
anywheresoftware.b4a.objects.collections.List _result = null;
int _i = 0;
char _ch = '\0';
int _d = 0;
String _combined = "";
 //BA.debugLineNum = 77;BA.debugLine="Sub CombineDigitWithList(digit As Int, existDigits";
 //BA.debugLineNum = 78;BA.debugLine="Dim result As List";
_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 79;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="For i = 0 To existDigits.Length - 1";
{
final int step3 = 1;
final int limit3 = (int) (_existdigits.length()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 82;BA.debugLine="Dim ch As Char = existDigits.CharAt(i)";
_ch = _existdigits.charAt(_i);
 //BA.debugLineNum = 83;BA.debugLine="Dim d As Int = Asc(ch) - 48";
_d = (int) (anywheresoftware.b4a.keywords.Common.Asc(_ch)-48);
 //BA.debugLineNum = 84;BA.debugLine="Dim combined As String = digit & d";
_combined = BA.NumberToString(_digit)+BA.NumberToString(_d);
 //BA.debugLineNum = 85;BA.debugLine="result.Add(combined)";
_result.Add((Object)(_combined));
 }
};
 //BA.debugLineNum = 88;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return null;
}
public static int  _digitsummod10(int _n) throws Exception{
int _sum = 0;
String _s = "";
int _i = 0;
char _ch = '\0';
int _digit = 0;
 //BA.debugLineNum = 44;BA.debugLine="Sub DigitSumMod10(n As Int) As Int";
 //BA.debugLineNum = 45;BA.debugLine="Dim sum As Int = 0";
_sum = (int) (0);
 //BA.debugLineNum = 46;BA.debugLine="Dim s As String = n";
_s = BA.NumberToString(_n);
 //BA.debugLineNum = 47;BA.debugLine="For i = 0 To s.Length - 1";
{
final int step3 = 1;
final int limit3 = (int) (_s.length()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 48;BA.debugLine="Dim ch As Char = s.CharAt(i)";
_ch = _s.charAt(_i);
 //BA.debugLineNum = 49;BA.debugLine="Dim digit As Int = Asc(ch) - 48";
_digit = (int) (anywheresoftware.b4a.keywords.Common.Asc(_ch)-48);
 //BA.debugLineNum = 50;BA.debugLine="sum = sum + digit";
_sum = (int) (_sum+_digit);
 }
};
 //BA.debugLineNum = 52;BA.debugLine="Return sum Mod 10";
if (true) return (int) (_sum%10);
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return 0;
}
public static anywheresoftware.b4a.objects.collections.List  _generatethreegradeidsfromsingledigit(int _startdigit) throws Exception{
anywheresoftware.b4a.objects.collections.List _result = null;
int _d1 = 0;
int _d2 = 0;
int _d3 = 0;
String _id1 = "";
int _g2 = 0;
int _d4 = 0;
int _d5 = 0;
String _id2 = "";
int _g3 = 0;
int _d6 = 0;
int _d7 = 0;
String _id3 = "";
 //BA.debugLineNum = 16;BA.debugLine="Sub GenerateThreeGradeIDsFromSingleDigit(startDigi";
 //BA.debugLineNum = 17;BA.debugLine="Dim result As List";
_result = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 18;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 21;BA.debugLine="Dim d1 As Int = (startDigit + 3) Mod 10";
_d1 = (int) ((_startdigit+3)%10);
 //BA.debugLineNum = 22;BA.debugLine="Dim d2 As Int = (startDigit + 6) Mod 10";
_d2 = (int) ((_startdigit+6)%10);
 //BA.debugLineNum = 23;BA.debugLine="Dim d3 As Int = (startDigit + 9) Mod 10";
_d3 = (int) ((_startdigit+9)%10);
 //BA.debugLineNum = 24;BA.debugLine="Dim id1 As String = startDigit & d1 & d2 & d3";
_id1 = BA.NumberToString(_startdigit)+BA.NumberToString(_d1)+BA.NumberToString(_d2)+BA.NumberToString(_d3);
 //BA.debugLineNum = 25;BA.debugLine="result.Add(id1)";
_result.Add((Object)(_id1));
 //BA.debugLineNum = 28;BA.debugLine="Dim g2 As Int = (startDigit + 1) Mod 10";
_g2 = (int) ((_startdigit+1)%10);
 //BA.debugLineNum = 29;BA.debugLine="Dim d4 As Int = (g2 + 3) Mod 10";
_d4 = (int) ((_g2+3)%10);
 //BA.debugLineNum = 30;BA.debugLine="Dim d5 As Int = (g2 + 6) Mod 10";
_d5 = (int) ((_g2+6)%10);
 //BA.debugLineNum = 31;BA.debugLine="Dim id2 As String = g2 & d4 & d5";
_id2 = BA.NumberToString(_g2)+BA.NumberToString(_d4)+BA.NumberToString(_d5);
 //BA.debugLineNum = 32;BA.debugLine="result.Add(id2)";
_result.Add((Object)(_id2));
 //BA.debugLineNum = 35;BA.debugLine="Dim g3 As Int = (startDigit + 2) Mod 10";
_g3 = (int) ((_startdigit+2)%10);
 //BA.debugLineNum = 36;BA.debugLine="Dim d6 As Int = (g3 + 3) Mod 10";
_d6 = (int) ((_g3+3)%10);
 //BA.debugLineNum = 37;BA.debugLine="Dim d7 As Int = (g3 + 6) Mod 10";
_d7 = (int) ((_g3+6)%10);
 //BA.debugLineNum = 38;BA.debugLine="Dim id3 As String = g3 & d6 & d7";
_id3 = BA.NumberToString(_g3)+BA.NumberToString(_d6)+BA.NumberToString(_d7);
 //BA.debugLineNum = 39;BA.debugLine="result.Add(id3)";
_result.Add((Object)(_id3));
 //BA.debugLineNum = 40;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return null;
}
public static int  _mostfrequentdigit(String _inputstr) throws Exception{
int[] _counts = null;
int _i = 0;
char _ch = '\0';
int _digit = 0;
int _maxcount = 0;
int _maxdigit = 0;
 //BA.debugLineNum = 56;BA.debugLine="Sub MostFrequentDigit(inputStr As String) As Int";
 //BA.debugLineNum = 57;BA.debugLine="Dim counts(10) As Int ' 0-9 digit counts";
_counts = new int[(int) (10)];
;
 //BA.debugLineNum = 59;BA.debugLine="For i = 0 To inputStr.Length - 1";
{
final int step2 = 1;
final int limit2 = (int) (_inputstr.length()-1);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 60;BA.debugLine="Dim ch As Char = inputStr.CharAt(i)";
_ch = _inputstr.charAt(_i);
 //BA.debugLineNum = 61;BA.debugLine="Dim digit As Int = Asc(ch) - 48";
_digit = (int) (anywheresoftware.b4a.keywords.Common.Asc(_ch)-48);
 //BA.debugLineNum = 62;BA.debugLine="counts(digit) = counts(digit) + 1";
_counts[_digit] = (int) (_counts[_digit]+1);
 }
};
 //BA.debugLineNum = 65;BA.debugLine="Dim maxCount As Int = 0";
_maxcount = (int) (0);
 //BA.debugLineNum = 66;BA.debugLine="Dim maxDigit As Int = 0";
_maxdigit = (int) (0);
 //BA.debugLineNum = 67;BA.debugLine="For i = 0 To 9";
{
final int step9 = 1;
final int limit9 = (int) (9);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 68;BA.debugLine="If counts(i) > maxCount Then";
if (_counts[_i]>_maxcount) { 
 //BA.debugLineNum = 69;BA.debugLine="maxCount = counts(i)";
_maxcount = _counts[_i];
 //BA.debugLineNum = 70;BA.debugLine="maxDigit = i";
_maxdigit = _i;
 };
 }
};
 //BA.debugLineNum = 74;BA.debugLine="Return maxDigit";
if (true) return _maxdigit;
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return 0;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Dim jobtag As String";
_jobtag = "";
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
}
