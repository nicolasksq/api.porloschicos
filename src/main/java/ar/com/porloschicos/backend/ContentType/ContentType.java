package ar.com.porloschicos.backend.ContentType;

public enum ContentType {
    CAMPAING("campana",1),
    PROYECT("proyecto",2),
    COMEDOR("comedor",3),
    NOVEDADES("novedades",4);

    private String type;
    private int id;

	ContentType(String type, int id){
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
