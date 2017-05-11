\* Problem: Path 1 - Roads *\

Minimize
 z: + 400 x12 + 950 x13 + 800 x14 + 1800 x25 + 900 x26 + 1100 x35
 + 600 x36 + 600 x46 + 1200 x47 + 400 x58 + 900 x65 + 1000 x67
 + 1300 x68 + 600 x78

Subject To
 c1: - x14 - x13 - x12 = -1
 c2: - x26 - x25 + x12 = 0
 c3: - x36 - x35 + x13 = 0
 c4: - x58 - x47 + x14 = 0
 c5: + x65 - x58 + x35 + x25 = 0
 c6: - x67 + x46 + x36 + x26 = 0
 c7: - x78 + x67 + x47 = 0
 c8: + x78 + x68 + x58 = 1

Generals
 x12
 x13
 x14
 x25
 x26
 x35
 x36
 x46
 x47
 x58
 x65
 x67
 x68
 x78

End
