B4J=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=10.3
@EndOfDesignText@

Sub Process_Globals
	Dim jobtag As String
End Sub




Sub Add7AndReturnLastDigit(n As Int) As Int
	Dim sum As Int = n + 7
	Dim s As String = sum
	Dim lastDigit As Int = Asc(s.CharAt(s.Length - 1)) - 48
	Return lastDigit
End Sub

Sub GenerateThreeGradeIDsFromSingleDigit(startDigit As Int) As List
	Dim result As List
	result.Initialize

	' ပထမတန်း ID (4 digits)
	Dim d1 As Int = (startDigit + 3) Mod 10
	Dim d2 As Int = (startDigit + 6) Mod 10
	Dim d3 As Int = (startDigit + 9) Mod 10
	Dim id1 As String = startDigit & d1 & d2 & d3
	result.Add(id1)

	' ဒုတိယတန်း ID (3 digits)
	Dim g2 As Int = (startDigit + 1) Mod 10
	Dim d4 As Int = (g2 + 3) Mod 10
	Dim d5 As Int = (g2 + 6) Mod 10
	Dim id2 As String = g2 & d4 & d5
	result.Add(id2)

	' တတိယတန်း ID (3 digits)
	Dim g3 As Int = (startDigit + 2) Mod 10
	Dim d6 As Int = (g3 + 3) Mod 10
	Dim d7 As Int = (g3 + 6) Mod 10
	Dim id3 As String = g3 & d6 & d7
	result.Add(id3)
	Return result
End Sub


Sub DigitSumMod10(n As Int) As Int
	Dim sum As Int = 0
	Dim s As String = n
	For i = 0 To s.Length - 1
		Dim ch As Char = s.CharAt(i)
		Dim digit As Int = Asc(ch) - 48
		sum = sum + digit
	Next
	Return sum Mod 10
End Sub


Sub MostFrequentDigit(inputStr As String) As Int
	Dim counts(10) As Int ' 0-9 digit counts

	For i = 0 To inputStr.Length - 1
		Dim ch As Char = inputStr.CharAt(i)
		Dim digit As Int = Asc(ch) - 48
		counts(digit) = counts(digit) + 1
	Next

	Dim maxCount As Int = 0
	Dim maxDigit As Int = 0
	For i = 0 To 9
		If counts(i) > maxCount Then
			maxCount = counts(i)
			maxDigit = i
		End If
	Next

	Return maxDigit
End Sub

Sub CombineDigitWithList(digit As Int, existDigits As String) As List
	Dim result As List
	result.Initialize

	For i = 0 To existDigits.Length - 1
		Dim ch As Char = existDigits.CharAt(i)
		Dim d As Int = Asc(ch) - 48
		Dim combined As String = digit & d
		result.Add(combined)
	Next

	Return result
End Sub


Sub AdjustDouble(d As Double) As String
	Dim intPart As Int = Floor(d)
	Dim decimalPart As Double = d - intPart
    
	If decimalPart * 10 > 5 Then
		intPart = intPart + 1
	End If
	If intPart =10 Then
		intPart = "0"
	End If
	Return intPart
End Sub
