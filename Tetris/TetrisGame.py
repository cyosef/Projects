import turtle, random

SCALE = 32 #Controls how many pixels wide each grid square is

class Game:
    '''
    Purpose: 
        Draws the board for the game, calls turtle functions to ensure graphics are running as fast as possible, and creates loop for game to work
    Instance variables: 
        self: allows for instances to be called across methods within Game
    Methods: 
        gameloop: loops the game to allow the pieces to visually move down the screen
        move_left: allows for the user to input left and move the piece left
        move_right: allows for the user to input right and move the piece right
    '''
    def __init__(self):
        #Setup window size based on SCALE value.
        turtle.setup(SCALE*12+20, SCALE*22+20)

        #Bottom left corner of screen is (-1.5,-1.5)
        #Top right corner is (10.5, 20.5)
        turtle.setworldcoordinates(-1.5, -1.5, 10.5, 20.5)
        cv = turtle.getcanvas()
        cv.adjustScrolls()

        #Ensure turtle is running as fast as possible
        turtle.hideturtle()
        turtle.delay(0)
        turtle.speed(0)
        turtle.tracer(0, 0)

        #Draw rectangular play area, height 20, width 10
        turtle.bgcolor('black')
        turtle.pencolor('white')
        turtle.penup()
        turtle.setpos(-0.525, -0.525)
        turtle.pendown()
        for i in range(2):
            turtle.forward(10.05)
            turtle.left(90)
            turtle.forward(20.05)
            turtle.left(90)

        self.active = Block()
        turtle.ontimer(self.gameloop, 300)
        turtle.onkeypress(self.move_left, 'Left')
        turtle.onkeypress(self.move_right, 'Right')
        self.occupied = []
        for i in range(21):
            row_lst = []
            for j in range(11):
                row_lst.append(False)
            self.occupied.append(row_lst)
        #These three lines must always be at the BOTTOM of __init__
        turtle.update()
        turtle.listen()
        turtle.mainloop()
    def gameloop(self):
        if self.active.valid(0, -1, self.occupied) == True:
            self.active.move(0, -1)
        else:
            for obj in self.active.square:
                col = obj.xcor()
                row = obj.ycor()
                self.occupied[row][col] = True
            self.active = Block()
        turtle.update()    
        turtle.ontimer(self.gameloop, 300) 
    def move_left(self):
        if self.active.valid(-1, 0, self.occupied) == True:
            self.active.move(-1,0)
        turtle.update
    def move_right(self):
        if self.active.valid(1, 0, self.occupied) == True:
            self.active.move(1,0)
        turtle.update


class Square(turtle.Turtle):
    '''
    Purpose:
        creates each square for the tetris pieces
    Instance variables: 
        x: block position on the x axis
        y: block position on the y axis
        color: the color of the block
    Methods: 
        __init__: this method creates each block the Square class
    '''
    def __init__(self, x, y, color):
        turtle.Turtle.__init__(self)
        self.shape('square')
        self.shapesize(SCALE/20)
        self.speed(0)
        self.fillcolor(color)
        self.pencolor('gray')
        self.penup()
        self.goto(x,y)

class Block:
    '''
    Purpose:
        this represents each of the tetris pieces along with the ability for the pieces to move and stack upon each other
    Instance variables: 
        self: allows for Block variables to be called across methods
    Methods: 
        move: creates new coordinates for each block pieces to move to 
        valid: creates boundaries for game to operate within
    '''
    def __init__(self):
        lst_colors = ['cyan', 'blue', 'orange', 'yellow', 'green', 'purple', 'red']
        rand_color = random.choice(lst_colors)
        if rand_color == 'cyan':
            self.square = [Square(3,21,'cyan'), Square(4,21,'cyan'), Square(5,21,'cyan'), Square(6,21,'cyan')]
        elif rand_color == 'blue':
            self.square = [Square(3,20,'blue'), Square(3,21,'blue'), Square(4,21,'blue'), Square(5,21,'blue')]
        elif rand_color == 'orange':
            self.square = [Square(3,21,'orange'), Square(4,21,'orange'), Square(5,21,'orange'), Square(5,20,'orange')]
        elif rand_color == 'yellow':
            self.square = [Square(4,20,'yellow'), Square(5,20,'yellow'), Square(4,21,'yellow'), Square(5,21,'yellow')]
        elif rand_color == 'green':
            self.square = [Square(5,21,'green'), Square(4,21,'green'), Square(4,20,'green'), Square(3,20,'green')]
        elif rand_color == 'purple':
            self.square = [Square(3,21,'purple'), Square(4,21,'purple'), Square(4,20,'purple'), Square(5,21,'purple')]
        elif rand_color == 'red':
            self.square = [Square(5,20,'red'), Square(4,20,'red'), Square(4,21,'red'), Square(3,21,'red')]
        
    def move(self, dx, dy):
        for object in self.square:
            new_x_cor = object.xcor() + dx
            new_y_cor = object.ycor() + dy
            object.goto(new_x_cor,new_y_cor)
    def valid(self, dx, dy, occupied):
        for object in self.square:
            new_x_cor = object.xcor() + dx
            new_y_cor = object.ycor() + dy
            if (new_x_cor < 0) or (new_x_cor > 9) or (new_y_cor < 0) or (occupied[new_y_cor][new_x_cor] == True):
                return False
        return True

if __name__ == '__main__':
    Game()
