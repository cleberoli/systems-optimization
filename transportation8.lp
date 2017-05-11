\* Problem: Transportation 8 - SuperMachine *\

Minimize
 z: + 14 x11 + 5 x12 + 8 x13 + 7 x14 + 9 x15 + 2 x21 + 12 x22 + 6 x23
 + 5 x24 + 7 x25 + 7 x31 + 8 x32 + 3 x33 + 9 x34 + 7 x35 + 2 x41 + 4 x42
 + 6 x43 + 10 x44 + 6 x45

Subject To
 c1: - x15 - x14 - x13 - x12 - x11 <= -1
 c2: - x25 - x24 - x23 - x22 - x21 <= -1
 c3: - x35 - x34 - x33 - x32 - x31 <= -1
 c4: - x45 - x44 - x43 - x42 - x41 <= -1
 c5: + x41 + x31 + x21 + x11 <= 1
 c6: + x42 + x32 + x22 + x12 <= 1
 c7: + x43 + x33 + x23 + x13 <= 1
 c8: + x44 + x34 + x24 + x14 <= 1
 c9: + x45 + x35 + x25 + x15 <= 1

Generals
 x11
 x12
 x13
 x14
 x15
 x21
 x22
 x23
 x24
 x25
 x31
 x32
 x33
 x34
 x35
 x41
 x42
 x43
 x44
 x45

End
