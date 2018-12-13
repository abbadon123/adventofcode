dic = {}
dic[1] = {}
dic[1][1] = 1
dic.setdefault(2, {})[2] = 2
print(dic)