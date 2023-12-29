# XML-Editor

## *INTRO*
* XML Visualization Project is a Scene Builder GUI application that allows the user to perform various operations on text input , such as validating, correcting, and compressing/decompressing XML and JSON files. It also provides functionality for loading and saving files, as well as a UI for displaying messages and prompts to the user. The code utilizes several helper functions defined in separate modules to perform these operations .


# **Presentation Video** : [XML-Presentation](https://drive.google.com/drive/folders/1E7m9oC1iu4sYVctfhpzSBhZgVlks5EkI?usp=sharing)


### Note: 
>Any change in the textbox as adding tags or removing tags must select text from the choice box  if not selected and press choose. 
>You have to compress first and save the compressed text in a text file `.txt` then decompress using the same saved text file.
## *Project Part#1*  

### Supported Features:
- Graphical User Interface (GUI)
- Error Detection and Correction (if any occur)
- Prettify
- Minify
- XML to JSON
- Compression/decompression
- Save file
- Load file

#### `Part1 buttons `
- `Choose file button`: beside this button there is choice box , you should choose to input your Xml code as text in text field or from file on your PC , if you do not choose and click on any other operation as minifying for example , you will get error message , for any of the following buttons , if you click on any one of them and you did not choose file then you will get alert message saying please choose file .
- `Validate button`: Once you click on it you will get an alert saying to you where the location of error if there is error exist.
- `Modify button`: It will correct Xml code and remove indentation and give you alert message saying the added open and closed tag to correct your code.
- `Minify button`: You should enter consistent Xml code, or you will get message saying that “File containing errors cannot minify “, if you enter consistent Xml code then you will get your code minified.
- `Json button`: it converts Xml code to Json code.
- `Compress button`:takes the input and produce (show dialogue) option  to choose position of file and save file in text form and as soon as file is saved , the file become compressed.
 
- `Decompress button`: you must click it before  clicking (report chose file),if you click decompress before click compress there will be error and there will be message “please select compress first”,and if file is already compressed it will open window to chose file that you compressed before and it will be output


## *Project Part#2*

### Supported Features:
- Graph Representation
- Network Analysis
- Post Search
- Graph Visualization
- Undo and redo 

#### `Part2 buttons `
- `Analysis button`: you have to choose functionality from choices box which are most active user,most influencer user,mutual followers between users ,suggest followers or post search and then click analysis it will open another window according to your choice
- `Undo button`:if you did not do any operation before, it will show alert contains a message”No more undo” and if there were operation it will go back to last operation before the current operation
 
- `Redo button`: if you did not do any operation after the current one , it will show alert contains a message”No more redo” and if there were operation it will go  to last operation after the current operation

- `Visualize button`: it show window contain visualization of our graph
