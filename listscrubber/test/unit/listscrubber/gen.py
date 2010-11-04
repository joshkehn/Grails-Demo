#! /usr/bin/python
import re, sys, csv, fileinput, random, md5

def getRandom():
    ret = []
    for i in range(0, random.randint(4, 20)):
        ret.append(str(random.randint(1, 10)))
        
    return ''.join(ret)



filept = open("million.list", "w")
filecsv = open("million_csv.list", "w")
filemd5 = open("million_md5.list", "w")
filesup = open("million_suppress.list", "w")
for i in range(0,1023040):
    email = getRandom() + "@" + getRandom() + ".com"
    filept.write(email + "\n")
    filecsv.write(email + ",\n")
    
    m = md5.new()
    m.update(email)
    filemd5.write(m.hexdigest() + "\n") 
    
    if(random.randint(1, 10) == 3):
        filesup.write(email + ", " + m.hexdigest() + "\n")