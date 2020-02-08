from PyQt5.QtWidgets import *
import sys

class Mwind(QMainWindow):
    def __init__(self):
        super().__init__()

if __name__ == '__main__':
    app = QApplication(sys.argv)
    mwind = Mwind()
    mwind.show()
    sys.exit(app.exec())