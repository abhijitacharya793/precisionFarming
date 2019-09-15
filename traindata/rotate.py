import os
import cv2
count = 677
for i in os.listdir('../blast'):
    try:
        img = cv2.imread(i)
        M = cv2.getRotationMatrix2D((150,150),90,1.0)
        rotated = cv2.warpAffine(img, M, (300, 300))
        cv2.imwrite(str(count)+'.jpg',rotated)
        count = count+1
    except:
        pass
