package be.kdg.java2.project.presentation.mvc.viewmodels;

public class DeletingViewModel {
    int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "DeletingViewModel{" + "ID=" + ID + '}';
    }
}
