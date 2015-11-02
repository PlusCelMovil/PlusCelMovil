package com.pluscel.pluscelmovil.dom;

/**
 * Created by Pablo Pincheira on 20/10/2015.
 */
public class Equipo {

    Members members;

    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }


    // nested class
    public class Members{

        DatoString marca;
        DatoString modelo;
        DatoString estado;
        DatoString imei;

        public DatoString getMarca() {
            return marca;
        }

        public void setMarca(DatoString marca) {
            this.marca = marca;
        }

        public DatoString getModelo() {
            return modelo;
        }

        public void setModelo(DatoString modelo) {
            this.modelo = modelo;
        }

        public DatoString getEstado() {
            return estado;
        }

        public void setEstado(DatoString estado) {
            this.estado = estado;
        }

        public DatoString getImei() {
            return imei;
        }

        public void setCurso(DatoString imei) {
            this.imei = imei;
        }

        // nested classes

        public class DatoString {
            String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public class DatoCurso {

            DatoTitle value;

            public DatoTitle getValue() {
                return value;
            }

            public void setValue(DatoTitle value) {
                this.value = value;
            }

            public class DatoTitle {
                String title;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }



    }
}
