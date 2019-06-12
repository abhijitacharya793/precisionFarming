import os
import markdown
import sys
import argparse
from distutils.dir_util import copy_tree

def input_file():
    # Takes -i and -n as input, input doc md file and its name
    ap = argparse.ArgumentParser()
    ap.add_argument("-i", "--input", type=str, required=True, help="path to md file")
    ap.add_argument("-n", "--name", type=str, required=True, help="name of md file")
    args = vars(ap.parse_args())

    # print(args["input"])
    # print(args["name"])

    # Read the file given as input
    with open(args["input"]) as f:
        doc = f.read()
    # Pass the name and doc content to create_folder
    return {"name":args["name"],"doc":doc}

def create_folder(name,doc):
    # print(name)
    # print("______________________________")
    # print(doc)

    os.mkdir(name)
    fromDirectory = "template"
    toDirectory = name
    copy_tree(fromDirectory, toDirectory)
    path = name+ "/" + name + ".md"
    # print(path)
    with open(path,"w+") as f:
        f.write(doc)

def md_html(name,doc):
    path = name+ "/" + name + ".md"
    html = markdown.markdown(doc,extensions = ['codehilite'])
    # print(html)
    return html

def final_html(name,html):
    print(name)
    print(html)
    print("______________________")
    path = name + "/final.html"
    with open(path,"a") as f:
        f.write(html)
        f.write("   </body>")
        f.write("</html>")

    with open(path) as f:
        final = f.read()
    print("final : {}".format(final))

def help():
    pass

if __name__=="__main__":
    try:
        ip_doc = input_file() # Input md file and save name
    # print("name = {}".format(ip_doc['name']))
    # print("doc = {}".format(ip_doc['doc']))
    except:
        pass
    try:
        create_folder(ip_doc['name'],ip_doc['doc']) # Folder with css and final and create .md
    except:
        pass
    html = md_html(ip_doc['name'],ip_doc['doc']) # convert md to html and highlight code
    final_html(ip_doc['name'],html) # Read md-html and put contents to final html body
    print("Done")
