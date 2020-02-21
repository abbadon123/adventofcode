(ns adventofcode-2019.day6.day-6
  (:require [clojure.test :refer :all]
            [clojure.string :as str]))


(defn number-of-orbits [input]
  (let [str->orbit (fn [x] (str/split x #"\)"))
        universal-map-reverse (->> input
                                   (map (comp vec reverse str->orbit))
                                   (into {}))
        directly-orbits (partial get universal-map-reverse)
        orbits-to-com (fn [object]
                        (take-while #(not= % "COM") (iterate directly-orbits object)))]
    (->>
      (keys universal-map-reverse)
      (map (comp count orbits-to-com))
      (apply +)
      )))

(deftest a-test
  (testing "small programs"
    (is (= (number-of-orbits ["COM)B" "B)C" "C)D" "D)E" "E)F" "B)G" "G)H" "D)I" "E)J" "J)K" "K)L"]) 42))
    ))

(run-tests)

(def input "6TJ)DQ7
Q64)6PD
9K5)F2C
NRH)456
Y7M)3FZ
SFM)RHP
PX5)FTR
RC8)8DP
3KT)XLP
4GC)ZBW
51K)DJQ
24V)4F3
4CR)C5D
RXZ)J74
9YN)M5V
4WK)S9L
ZHC)XQJ
7L9)Y1Z
HRS)VZB
C41)9SZ
V8V)DXG
1J4)GGW
BZF)D86
B16)WW2
RK4)HZT
HRF)Q3M
GQJ)K6C
WC1)918
S4T)YHG
L6V)THN
VFP)FG3
G4K)M4S
BRH)7QS
J7D)ZWP
BKS)86D
FNK)8Y9
L7N)LSW
XGD)SSD
V79)7F3
7J4)FSR
BN1)V6L
8VP)82W
9YZ)1JB
7LM)M16
M1X)P72
ZYV)9WT
N6F)RX5
FNK)SQR
MSK)FYY
ZQQ)1KS
K15)BFM
QHP)ZRP
C2W)2SM
CGC)631
CZB)5PS
7FY)4TS
T24)F53
LPY)L7N
TG1)QVP
M5V)248
B3G)N85
YZG)JWC
R4H)XGS
ZHN)C2V
3TN)WPF
218)ZQ3
HL6)KQL
917)S6P
Y3N)FLV
Y9H)J3G
RVZ)GPX
KBG)ZK1
VP5)ZNS
R27)43Z
JLM)XGW
R5M)MYB
D1R)38M
CGL)C9R
ZYR)SWB
R65)WYB
DZX)N3S
ZLF)P7B
3JG)GPL
WMY)6HY
KTF)619
RKS)JK4
CJB)F1N
J69)CJB
GFC)CVX
4C8)T1F
SSD)389
LYR)WR7
99Y)188
3YC)Q7Z
4BB)ZHK
8LY)JYN
WMB)X2L
B7L)KSZ
G2J)3JG
C1X)SHP
3Q4)7JH
CQS)HDY
1XL)JTM
BGY)19Q
N85)2D3
SQR)8V5
3PD)HVF
Q13)N43
3FV)MLH
662)GL8
1ZR)3DL
RZB)P1V
CJB)MBZ
HLZ)9FT
77R)DWB
5N2)4CR
PMX)WMY
8TD)11L
2JN)H1W
VN8)KVV
4W2)FQ2
6Y1)Y7G
VGK)CXF
VM9)1VZ
MHR)67K
RH9)88M
X3G)L9Y
BBX)PHF
VCS)26F
51T)K1Q
ZJL)YPP
Q8Z)Q64
9RP)H64
8RX)VZD
59F)16L
6T5)8QP
CJG)2DD
748)JN5
NGC)N3V
BVN)Y7L
S26)Y3N
R7M)WCJ
CQS)WJ5
57T)RVJ
BP7)WC2
BW7)LB9
ZH9)VW7
X8R)K7M
RJW)5WQ
KQV)HB2
HJ7)9RP
KYM)ZNY
5PS)TCD
PB7)RSP
BR8)DC8
JM4)W61
H7W)BJB
NRW)56F
1NV)169
38M)JLM
J7D)8VB
4H9)1GZ
BC1)Y9S
C9R)6L3
L4X)Y8Q
MP6)WLZ
HZT)Y3X
PXN)637
GCS)ZT1
G84)BW7
9T5)4L2
MJQ)R28
3R7)9CC
HVX)6V4
9Y2)GW6
S3F)TPP
C39)LTB
MYB)SHN
5ZC)WBC
ZWP)SJQ
8QD)MKV
TKZ)KKR
7J4)B8T
R52)295
BRR)PV3
THN)3DM
S42)R1D
FD8)QJ4
WB1)RYK
6JL)2JR
631)RWV
C5T)NLD
CC4)KNF
SJL)4C8
QNV)T5P
D5D)D6P
Y97)Q2G
7C7)Z8T
5BS)B9C
BWQ)WM6
T5P)SPN
3DL)YV4
Q34)GCS
W71)GP1
GDZ)CH6
WT6)S3F
16L)THM
CPY)XDC
P48)BP5
V9C)PM1
FFP)QWN
V9C)8RX
9T9)HDD
WYW)48L
VJP)S26
3B9)N7H
88M)RJ3
8YV)NHS
QDV)47S
3WJ)YOU
M2Z)33N
6L3)2RZ
JGL)L92
NP3)L6G
BDW)M4Z
C1X)8VP
484)7PL
VX3)GGQ
RK8)ZRZ
F4N)MRT
NJK)XH1
VDN)X4J
SHN)YQ1
KZ4)5LS
C75)K15
S5J)265
DJQ)8LR
MSP)BTB
2M8)7BN
H9J)J3Z
BSR)421
1J4)HBR
CLK)V8D
VZB)DYF
4WN)GB7
MGD)BML
5VY)3DJ
H8F)B3G
M9L)YSL
K3N)DCV
BLV)95B
QMY)RHH
9TF)J5F
29Q)6JL
QDV)PNS
Y2P)SST
NK9)H5Q
1JB)V79
ZY7)1LF
F53)K5Z
BP5)R4H
SMJ)TZV
LCW)8GV
L5H)3LJ
J1X)JS5
S9L)FD8
28Z)5G3
29D)QFT
M7X)DZ1
G4L)WN6
2M1)DDW
77R)PR6
SWM)TQP
DC8)VM9
CYX)SJL
ZRZ)HSJ
XP4)BRH
8HG)HT6
D3Y)59Z
KVV)3SL
WNQ)LBL
CJ1)5BS
19Q)C5C
NWH)XCC
PXS)KKB
F8P)BLG
WR7)8ND
K6C)BDW
YHG)R2F
2JR)R4P
619)Z1X
93D)BZF
51H)Z9H
9WB)9SV
SFM)H7W
K7P)Y65
B8T)CTK
2DD)9VC
ZM4)TQZ
733)17J
FCM)8V4
CHH)4W3
G2S)XLB
BQC)1XL
WFM)NH4
927)CFB
J74)3W8
SDC)9WB
Q79)RWL
46L)P8L
T1F)4G3
1N3)VX3
4KG)111
Z4L)BC1
QH5)GP2
3R7)VLV
F3H)PKS
Z8D)VLR
YZC)78K
3XL)VCB
L9L)Z8D
44K)WNQ
6Z1)3WJ
78S)FVB
D1C)HNG
GJG)MWM
W7J)RX6
7PL)QCB
KRQ)XP1
BWQ)3ST
XMF)1DS
R7V)Z4L
FCS)MZP
5SF)W2W
HCK)H72
ZY2)N6F
7FY)BNM
1ZP)NJK
Y87)Q4S
898)YD1
8K6)P9Q
ZG3)13D
XLB)MP6
QPD)3TP
R1R)KQN
VFF)TQK
SGZ)832
Z3F)QPD
LTB)8QD
N3S)FFP
S5X)2WN
HDD)RC8
6JN)Q9M
9TV)PJ8
1W5)SGZ
55F)RLD
J83)6YG
TK4)RZ8
TSD)CPY
78W)C3L
8VP)CST
13D)NCH
MP5)HT2
5PZ)ZDB
WYS)J2F
L7Q)V4X
6V4)59F
RS2)SW6
3DJ)L7Q
3LJ)NWH
4F3)6Z1
SJZ)QHP
94Q)5V7
4W3)L5J
52V)64D
V7G)C27
FTR)NPC
MPT)K8K
CVX)X3G
9WN)5FX
NLD)TG1
Q2F)V3G
WW2)YRT
LBC)QSZ
SZP)4KV
ZNY)K3N
YWP)T24
LYF)JV8
FGP)4TJ
H6G)7Y8
5JV)8D1
KKB)6TJ
2SZ)5DY
QWY)ZY2
MBN)RZF
N79)N7S
B29)3J2
Y7G)N5K
6W8)C38
DVY)TJP
78K)RJW
HT6)HKG
VHL)NK9
GGW)X8G
RMP)B4T
DRD)B16
KSZ)RPM
R1D)YWS
RHH)WG6
BML)W11
DWB)4YF
WJ5)4KG
F9J)RPT
J1X)B29
JK4)HS1
RK4)52V
TZ1)CC4
21W)4PM
RX5)KMC
M1X)N7V
SP4)Z3W
LYH)8HG
95S)Y3Q
SW6)5PZ
2N3)46L
PCX)5YW
VLB)HF2
RJJ)GJG
Q95)3W1
SP1)86H
4ZR)8X5
Y8Q)GNY
YRC)9K5
GL8)QDR
TSZ)13Q
D4C)F8X
436)D3Y
Z3F)2BX
VKM)PWT
MM5)M8G
KVB)MT3
WWW)N79
S8F)1N3
8DP)M79
GQP)2QL
7VR)8G8
PD7)R3M
94D)564
5XP)WL1
8LY)P9J
D3D)94L
56F)2BK
NCS)BQC
4GZ)S5J
GM2)NRH
XGW)1MC
ZDB)K35
ZRP)CDL
SYH)J7T
Q4S)KBG
K7M)G3Q
9PG)N91
J7T)LRH
2ND)Z3F
9T9)CJ1
ZR5)FDC
26F)L5H
4FK)Z4N
3PP)BHG
BN1)QVM
JN5)FF7
ZH4)SWM
Z3W)XVK
VPD)G3Z
K15)VKM
7H3)9LV
HCK)KRQ
TZV)ZY7
LDW)CLK
3DM)C4J
MRT)29D
5Y3)XYV
5RK)6N6
L21)JDN
T43)24V
BNM)4VT
DMJ)2VZ
CJ1)4H9
9C5)V9C
3JF)1X4
8V4)ZKW
42N)62V
WSL)TTL
KG6)SM4
9VC)662
1VZ)Y97
1BD)B4B
P9Q)R65
918)9M1
XP1)4W2
XPY)46Y
3XW)YR6
HZY)Q5G
DQ7)VHL
J2F)ZZG
Q5G)28Z
Z1X)HZY
7V3)FBS
48L)LZB
HVG)TSD
HHY)KQV
MBB)CTM
HB2)LD7
9DW)Q95
446)3Q4
LBT)GFS
5RB)C5T
Z68)PNV
NJ7)TMH
VBT)M7W
QVP)Q2S
YSZ)R5M
7QS)LZW
Q3K)B6R
C39)HRF
74S)1FV
BMZ)RSC
4WR)M3P
ZFQ)7RW
XPY)JKH
D6P)FDL
RHP)JRW
6YP)LNN
Y3Q)C9K
9FT)SZP
D6V)PD7
QY7)51K
ZS7)2BH
J5F)ZG3
S7L)WN4
F8X)9S3
VG5)S1L
YV4)ZHN
G3Z)Q8Z
V8D)C9T
389)VF8
J2F)9ZS
H8F)R31
43Z)2S4
GFS)74R
LT1)CQV
TJP)TBG
XBV)DZT
KV8)T6V
5YN)MGD
7RF)P2D
46Y)J7D
B41)FSZ
B6T)34W
XGX)Z6W
564)M39
94L)HJ7
8DG)K51
XH5)3GT
DZM)JSS
X2L)LZX
RQT)VCS
JSS)XVB
93M)Y87
9SQ)D5D
6YG)6X2
J3M)Y7M
K75)9KN
NH4)9FH
8NV)2X6
Y9S)Z48
8BV)2M1
2YJ)2ND
M4S)WCZ
HJG)NYK
WM6)KQP
N7L)FGP
9NC)LKP
BSV)FHP
G39)5Y3
P52)FNK
LG7)ZSD
9M1)ZCL
1BM)C6N
V3G)9T5
18V)YHM
COM)Q3K
PB2)Q13
LZW)7HR
8LR)D5X
W4Z)BBX
HVF)25Q
2M1)1DQ
ZHK)3PP
WC2)KCX
VQV)5GS
QVM)WYW
C9K)W2N
ZGC)CJG
TWJ)7LL
DZ1)SDC
9FS)8XV
9FH)MP5
J3Z)MHR
NG8)4FK
CNQ)1RR
4L2)QMY
QSZ)NCV
H8Q)PWS
1V8)GMS
QPN)XGD
RPJ)T1J
48L)PTK
4YD)KYM
B16)C41
CDF)GSC
77N)622
QCB)SYH
RDK)Q34
7Y8)S5V
N1D)L6V
M1T)P48
GRY)1GW
D7D)D3D
QQK)DL3
6PD)8R8
GP2)S5G
HFW)4MC
111)9TV
LQQ)HJG
TQK)Z68
BJB)DT6
1GZ)Z7F
XDC)N1D
5VR)B41
418)B4J
D9F)XY6
P1V)WYS
9CC)NQK
KRL)656
XY6)917
34W)CQS
JDN)SS9
KG8)8DG
CXF)L95
Y2J)MPT
H5Q)3WS
LVL)J3M
LGF)YHN
FDL)N7L
WT2)9NC
HF2)VYL
622)Z5F
5S5)QGS
QSH)H6G
FG3)ZD3
816)1B9
K51)7RF
N91)95S
3CJ)P9G
NXS)PMX
D1C)GY8
13D)QDV
GYD)Q2F
MK4)HL6
VCB)DF3
8S6)LT1
Z6W)ZRM
B4J)4WK
5JF)GDZ
H3X)4GC
MQC)R27
8GY)7TT
Z5F)KST
HPH)JGB
PV3)9YN
YRC)BZX
JWC)3JF
62V)Q8V
V9V)53P
P2D)XH5
3QP)DMJ
HBR)XGX
LJR)JNS
7WB)5JF
P7B)55F
L6G)WFM
QW6)F4N
HQJ)LGS
8VB)WC1
637)94Q
4Z1)96W
3Q2)LGF
N5K)J69
RC3)X63
9GD)LQ5
NKB)NRW
4V1)DNY
CK4)5RB
P1V)35T
M39)LDW
DT6)K5C
LGF)SMJ
67J)WH8
839)8X6
Q3K)L7Y
N8R)DZ8
NLD)BG7
4K6)6CT
3JN)BR6
8ZY)9RQ
1KS)F9J
ZL4)LJR
4FC)7J4
RPM)M33
XYV)V35
F7K)9T9
N1S)WB4
C5C)8BV
RSC)CNQ
7TQ)QQK
K58)5VR
H6R)23Q
LGS)9F5
G1S)N1S
9LV)KB6
Y3D)5YN
PX5)5S5
XCC)H9J
YHN)YQS
CDL)W4Z
3Q9)VFF
J7T)112
WH8)Z33
Q8T)RK8
GRY)X8D
VW7)1B6
WL1)MM5
M33)NXS
2S4)BYM
X63)JPZ
28D)V1F
TTJ)161
2DF)RMP
D6Y)CDF
JYN)TYS
QFT)9SQ
GPL)KX1
35T)6YP
9DY)D6V
13Q)TK4
K6V)7YK
21B)L9L
MF8)NM2
VZK)6XC
TTL)425
6X2)RDK
95S)WMB
4Z6)D1R
JNS)K7P
JS5)1V8
VLR)43P
6N2)77N
BFQ)Q1Q
TBG)NJ7
2D3)3JN
JPZ)CL5
5DY)5RK
3PD)T52
TMH)8B7
LLW)NKR
XB1)HCK
7F3)S9Q
Z68)RJJ
4VT)3KT
L7Y)KG6
29W)25N
PKS)N9G
3MW)V7G
112)4D2
DYC)CGC
3FZ)S42
Y9H)DD7
NBB)Q2Y
Z39)MJT
9S3)3FK
LRH)V9V
RQG)MLF
4FC)29Q
TQP)M6S
1B6)8TD
4WK)S4T
T3B)CHP
B6R)77R
G6D)ZL4
416)PRQ
RP9)RS2
Q1T)18V
N7V)LYH
265)1YW
C2V)KKP
RZF)QSH
SGM)ZJL
6HY)8YV
YQS)S8F
GSC)1J4
53P)TZ1
T52)DMN
L9Y)B6H
XH1)436
6WZ)GQN
MXK)D2T
SS9)HPH
S4V)QRJ
ZRM)5JV
59X)9PG
2X6)M1X
MTV)PW6
R1F)DZM
2FH)28D
H1K)6N2
3GT)3Q9
KST)Q1T
M6S)3TN
SJQ)CYX
MP5)B5J
SHP)67J
ZCL)J2S
1RR)YK9
V8M)MBN
MRT)Z8Q
K5Z)839
BTB)FP1
XVV)KLG
8XK)SYB
W71)Z39
GB7)93C
LQ5)RXZ
THM)ZQQ
NVV)ZGC
TSG)5GG
YPP)BSV
S87)ZBD
NCZ)7MJ
TQZ)3XL
3ST)GTF
L95)8ZY
H1W)8V8
NYK)7BF
B9C)1NV
1GZ)9C5
K1Q)4V1
Q2G)RZB
G1S)93M
RRB)G4L
TWR)VJP
SXN)HFQ
Q5V)RN6
NCV)PB7
Q1Q)9DY
ZM4)C75
7RW)6BD
RCQ)FWP
DF3)RDV
QHP)ZPK
C78)8LY
92N)N8W
QK6)YF4
PTK)J1X
LD7)NCG
1QM)XLX
W2W)FYG
25N)J19
SKS)1BM
J57)C78
T6P)RC3
PW6)9FS
TWR)B2J
S3F)GQP
7JH)L1H
48H)VBT
WCX)DVY
5XL)WYH
H6F)MSK
FLV)3X9
JGB)4GZ
LNN)VLK
1T5)QTX
SST)RK4
DNY)TSZ
M4Z)48H
3FK)LQQ
B4B)7H3
QFY)JM4
SYB)MXK
74R)WT6
C3L)P44
95B)78S
2VZ)SJZ
1NY)5N2
5GS)TTJ
PWT)4K6
JKH)BVN
93C)LVQ
SPN)GFC
72M)NBB
Y3X)3QB
8NH)416
ZNS)XPY
3QJ)ZJ3
859)NP3
XVK)3FV
8FZ)BWW
FYY)MSP
B5J)81G
TGW)SGM
61H)TDB
Z7F)BMZ
5FX)JVP
1LF)S1Y
WYB)HVS
3W1)VPD
NQK)446
5PS)HQJ
H64)D6Y
RX6)M2Z
W61)NMJ
CHH)BGY
7TT)LCW
LBL)G1S
PJ8)XD8
D1R)XXJ
CV6)KJ2
3TP)ZH9
KJ2)847
126)XB1
1KH)TNT
TS8)CV6
FBS)9TF
NHS)3QJ
FP1)1BD
SWB)87F
ZKT)QX7
1ZR)1QM
QX7)56W
59Z)ZLF
XLX)4JQ
B3S)W7J
2JR)2GZ
72G)V8V
R7M)Y3D
GC9)3R7
SM4)4WR
456)3CJ
8NV)92N
S9Q)CGL
CY5)7FY
1RZ)BR8
FYG)2M8
JVP)GM2
5C9)898
MNQ)218
RWL)6WZ
1MC)PRV
11L)BN1
L5J)R52
5V7)CZB
Q2Y)G4K
FSZ)DMR
FVB)D7D
TB1)51T
LZX)PXG
47S)859
Q3M)3XW
HKG)51H
DMR)HLZ
M61)TKZ
8ND)ZM4
S1Y)XP4
8R8)DZX
MZP)1ZR
HNG)M1T
4TS)KJX
KLG)K75
VLK)ZTM
CQV)TSG
KJX)SXN
CH6)VN8
LZB)1NY
ZSD)B7L
QGS)FNV
JVP)NKB
BYM)SP1
ZFQ)9PJ
HNG)5SF
29G)QWY
RYK)FXM
S5V)B6T
Z9H)89Q
ZPK)LG7
SF5)BKX
43P)KRL
2XX)Y2P
CTK)J57
YZG)MPF
2QL)4Z1
LKP)9R9
VLV)GYD
DZ8)S4V
TBG)RQT
B4T)YC2
GTF)C1X
SS9)W71
R9L)QY7
KB6)KG8
MWM)MNQ
T14)Q79
YWS)8K6
HSJ)LFQ
JZM)BWQ
C4J)WB1
Q7Z)Z14
8XV)4HW
HN6)247
FXR)72M
9F5)6Y1
WCJ)MJQ
PRQ)YSZ
4MC)HBW
HV7)29G
295)8QG
FHP)T6D
MCG)PB2
4KV)L1V
61H)F3H
N3V)YWP
8ND)VDN
WYW)CY5
2SM)RP9
RZ8)WSL
9SQ)HFW
4PM)1T5
1GB)M61
FF7)5BB
81G)RDT
23Q)78W
FDP)G6D
JGS)P6J
KQN)MY9
MQZ)HVG
Z33)KV8
3Q9)QNV
12V)WHV
17J)GRY
TTJ)JZM
GP2)VQV
5WQ)9YZ
8NH)MSC
248)FDP
FSR)PCX
C5D)DM5
RDV)BKS
M7W)2XX
QDR)G2J
W5T)927
R4P)Q8T
169)LPY
7QS)LBT
9SV)F7K
N9G)3MW
247)7C7
Z4N)SKS
9KN)733
Z54)ZFQ
5JF)C39
V8D)7WB
Y1Z)YRC
4YF)W5T
TPP)MQC
VSP)PXS
DBS)19K
QR1)CSF
QJ4)1RZ
D5X)MK4
B6H)VZK
1FV)F1J
HS1)LW7
Z6M)Q5V
J19)9DW
PR6)V7W
86D)VZZ
N8W)LVL
LTR)RVZ
ZBD)4WN
WN4)ZYV
HBW)K58
5QD)6T5
8V8)PX5
FQC)3QP
C38)748
MLF)59X
KQP)XR4
H72)BR7
LW7)G38
QM2)4BB
NCH)C7X
CTM)QR1
TNT)XVV
33N)PXN
FDC)KTF
FQ2)6W8
K35)FCM
16L)MBB
MTM)6MN
ZKW)44K
PKS)RKS
WG6)2XV
V7W)HDC
9RQ)S7L
MPF)G84
DNY)T6P
3GX)2YJ
378)VP5
Y8Q)99Y
29Q)BSR
T6V)P52
VZD)X8R
HVS)GQJ
8TW)LTR
9SZ)LJB
X8G)3PD
NMJ)X5L
B51)SWG
PHF)YZG
LJB)DNR
YC2)MF8
LVQ)LYF
5LS)RPJ
4TJ)NH3
8D1)KVB
S1L)7LM
RSP)8B2
JYF)DBS
K5C)Y9H
4HW)NGC
4D2)G2S
9WT)LG5
ZD3)NCZ
Y9C)C8F
M3P)418
8X5)KZ4
YHM)29W
59F)FXR
XLP)R1R
KJ2)2DF
YF4)YQL
Z14)M9L
PNV)R7M
ZBW)1KH
8G8)5HF
8X6)484
G3Q)12V
8GV)3Q2
421)2N3
W2N)K6V
V1F)YZC
188)SP4
PWS)B3S
5Q5)R9L
T2C)5XL
TYS)GC9
2GZ)ZVH
5YW)FCS
T6D)TWK
2BK)XMF
TPP)W47
HDC)VGK
X8D)S87
Z8Q)XBV
7YK)NCS
GQN)H8Q
CSF)5C9
BFM)TGW
MLF)HHY
P9G)RRB
Q2S)VLB
PTK)ZR5
BZX)TB1
2RZ)9Y2
BR7)NG8
QH5)13K
6BD)M7X
WYH)Z6M
ZVH)F8P
4JQ)51D
XVB)D4C
1YW)TD6
RHH)JYF
M79)8NH
GP1)5ZC
YQ1)QW6
1YL)MTV
HN6)D1C
1DQ)BRR
DDW)21W
F2C)CK4
19Q)T3B
MT3)42N
X4J)TNV
Y65)MCG
KKP)H2F
ZP7)QPN
CST)1GB
QR1)8TW
C9T)8NV
SWG)H3X
BKX)1ZP
8GY)H8F
PRV)N8R
8V5)WWW
LSW)SFM
C6N)JCY
1GW)PQS
WCJ)74S
KNF)4Z6
2WN)T14
PQS)5QD
6XC)NVV
DNR)ZYR
MRC)T43
3QB)MQZ
X5L)MRC
9ZS)ZKT
TDB)2SZ
M16)7L2
TWK)TS8
G72)72G
NPC)8S6
56W)7V3
WBC)DBL
6MN)3B9
JRW)HRS
JCY)RQG
2XV)RH9
3J2)61H
Z8T)8FZ
2QL)QK6
7L2)9WN
RPT)D9F
RDT)8GY
1ZP)69S
BWW)7TQ
C8F)FQC
3X9)78H
MLH)TWR
7MJ)JGL
NCG)LYR
LG5)Y2J
W4Z)4ZR
5HF)SF5
7BF)V8M
WLZ)5Q5
CFB)PH8
425)189
LYF)21B
96W)5XP
G38)9P7
LFQ)GJ2
YQ1)MHM
ZJ3)57T
T14)VFP
V4X)8HT
RVJ)C2W
J3G)L4X
161)5Q1
GNY)4YD
QWN)QFY
HBR)3V3
ZR5)QH5
3V3)WT2
JGB)G39
8B7)QM2
2CD)BP7
6CT)WCX
LB9)G72
R2F)8XK
K58)J83
WB4)HV7
BHG)17Z
RN6)2FH
67K)Z54
KMC)B51
51D)ZHC
TD6)2CD
189)3YC
YR6)JGS
8X5)H6R
PH8)6JN
PNS)VG5
KX1)1YL
TNV)93D
GW6)378
HDY)126
5FX)HVX
ZF7)TWJ
P72)ZH4
SMJ)1W5
FXM)T2C
ZZG)DYC
N7H)2X2
T1J)YDQ
Y7L)S5X
L1H)RCQ
S6P)Y9C
ZRM)3GX
GY8)R1F
YSL)SAN
DD7)ZS7
F1N)CHH
4WN)94D
XR4)816
8QG)ZF7
H2F)BLV
KQL)7L9
C7X)MTM
R3M)4FC
92N)ZP7
JRW)VSP
13K)DRD
LPY)H6F
R28)LLW
GPX)5VY
DMN)H1K
8QP)VNK
MKV)LBC
N7S)5ZJ
9PJ)2JN
YDQ)HN6
NKR)GPW
25Q)7VR
S5G)L21
64D)9GD
DBL)R7V
TCD)BFQ
QTX)G92")

(number-of-orbits (clojure.string/split-lines input))