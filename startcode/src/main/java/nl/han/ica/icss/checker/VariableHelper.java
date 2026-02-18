package nl.han.ica.icss.checker;

public class VariableHelper {
    private String datatype;
    private String name;
    private boolean isLocal;

    public boolean getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public void setDatatype(String newDatatype){
        datatype = newDatatype;
    }

    public void setName(String newName){
        name = newName;
    }

    public String getDatatype() {
        return datatype;
    }

    public String getName() {
        return name;
    }
}
