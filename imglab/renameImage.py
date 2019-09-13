import os
i = 201
dir = "brownspot/"
for filename in os.listdir(dir):
    #print(filename)
    tmp = filename.split(".")
    if tmp[1]=="png" or tmp[1]=="PNG":
        print(filename)
        os.rename(dir+filename,dir+str(i)+".png")
    i = i + 1
