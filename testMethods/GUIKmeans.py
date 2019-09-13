from tkinter import *
from tkinter.filedialog import askopenfilename
from PIL import Image, ImageTk
import tkinter.simpledialog
import cv2

root = Tk()

#setting up a tkinter canvas
w = Canvas(root,width=300,height=300)
w.pack()

#adding the image
original = Image.open("/home/katsuro/Documents/repo/precisionFarming/testMethods/392.jpg")
img = ImageTk.PhotoImage(original)
w.create_image(0,0,image = img,anchor="nw")

def printcoords(event):
    print (event.x,event.y)

w.bind("<Button 1>",printcoords)


root.mainloop()
