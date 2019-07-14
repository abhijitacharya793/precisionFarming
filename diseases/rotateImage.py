import os
import cv2
dir = "brownspot/"
i = 392
for filename in os.listdir(dir):
    tmp = filename.split(".")
    img = cv2.imread(dir+filename)
    (h,w) = img.shape[:2]
    center = (w/2,h/2)
    M = cv2.getRotationMatrix2D(center,90,1)
    rotated90 = cv2.warpAffine(img,M,(h,w))
    #cv2.imshow('Original Image',img)
    #cv2.waitKey(0) # waits until a key is pressed
    #cv2.destroyAllWindows() # destroys the window showing image

    #cv2.imshow('Image rotated by 90 degrees',rotated90)
    #cv2.waitKey(0) # waits until a key is pressed
    #cv2.destroyAllWindows() # destroys the window showing image
    cv2.imwrite(dir + str(i) + "." + tmp[1],rotated90)
    i = i + 1
