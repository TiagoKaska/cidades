# cidades
This application was generated using JHipster 4.7.0, you can find documentation and help at [https://jhipster.github.io/documentation-archive/v4.7.0](https://jhipster.github.io/documentation-archive/v4.7.0).


#FUNCIONALIDADES

1- Importar dados do CSV para a base. 

    src/main/resources/config/liquibase/changelog/20180130223531_added_entity_City.xml

2 -  Retornar somente as cidades que são capitais ordenadas por nome.

    GET [http://localhost:8080/api/cities/capitals]()

4 - Retornar a quantidade de cidades por estado;

    GET [http://localhost:8080/api/cities/count/SC]()

5 -  Obter os dados da cidade informando o id do IBGE.

    GET [http://localhost:8080/api/cities/ibge/1200138]()

6 -  Retornar o nome das cidades baseado em um estado selecionado;
    
    GET [http://localhost:8080/api/cities/uf/RS]()

7 - Permitir adicionar uma nova Cidade.
    
    POST [http://localhost:8080/api/cities/]()
 
 
     JSON {
            "ibgeId": 8557, "name": 
            "teste nova cidade2", 
            "uf": "SC",
            "capital": false,
            "lon": -8388388,
            "lat": -23434,
            "noAcents": "noAcents",
            "alternativeNames": "",
            "microRegion": "",
            "mesoRegion": ""
         }

8 - Permitir deletar uma cidade;
    
    DELETE [http://localhost:8080/api/cities/1]()
    Passando o Id da cidade

11- Retornar a quantidade de registros total;
    
    [http://localhost:8080/api/cities/count/all]()


