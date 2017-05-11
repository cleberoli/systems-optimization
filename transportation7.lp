\* Problem: Transportation 7 - Wine *\

Minimize
 z: + 20 x11 + 16 x12 + 24 x13 + 10 x21 + 10 x22 + 8 x23 + 12 x31
 + 18 x32 + 10 x33

Subject To
 c1: - x13 - x12 - x11 >= -300
 c2: - x23 - x22 - x21 >= -500
 c3: - x33 - x32 - x31 >= -200
 c4: + x31 + x21 + x11 >= 200
 c5: + x32 + x22 + x12 >= 400
 c6: + x33 + x23 + x13 >= 300

Generals
 x11
 x12
 x13
 x21
 x22
 x23
 x31
 x32
 x33

End
