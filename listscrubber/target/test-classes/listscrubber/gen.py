#! /usr/bin/python
import re, sys, csv, fileinput, random, md5

def getRandom():
    ret = []
    for i in range(0, random.randint(4, 20)):
        ret.append(str(random.randint(1, 10)))
        
    return ''.join(ret)


def getEmail():
    emails = ['josh@kobemail.com', 'saif@kobemail.com', 'production@kobemail.com', 'josh.kehn@gmail.com', 'ror@sucks.com']
    return emails[random.randint(0, 4)]


filept = open("million.list", "w")
filecsv = open("million_csv.list", "w")
filemd5 = open("million_md5.list", "w")
filesup = open("million_suppress.list", "w")

print "Outputing..."

for i in range(0,1023040):
    # email = getRandom() + "@" + getRandom() + ".com"
    email = getEmail()
    filept.write(email + "\n")
    filecsv.write(email + ",\n")
    
    m = md5.new()
    m.update(email)
    filemd5.write(m.hexdigest() + "\n") 
    
    if(random.randint(1, 10) == 3):
        filesup.write(email + ", " + m.hexdigest() + "\n")
        
    sys.stdout.write(str(i) + "\r")