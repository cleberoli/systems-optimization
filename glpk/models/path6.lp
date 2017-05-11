\* Problem: Path 6 - Routes *\

Minimize
 z: + 4 x13 + 4.5 x14 + 9 x15 + 8 x16 + 8 x23 + 8 x24 + 8 x25 + 7 x26
 + x34 + 6 x35 + 5.5 x36 + 6 x45 + 5 x46

Subject To
 c1: - x16 - x15 - x14 - x13 >= -100
 c2: - x26 - x25 - x24 - x23 >= -200
 c3: - x36 - x35 - x34 + x23 + x13 >= 0
 c4: - x46 - x45 + x34 + x24 + x14 >= 0
 c5: + x45 + x35 + x25 + x15 >= 150
 c6: + x46 + x36 + x26 + x16 >= 100

Generals
 x13
 x14
 x15
 x16
 x23
 x24
 x25
 x26
 x34
 x35
 x36
 x45
 x46

End
