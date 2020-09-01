$(document).ready(function () {
    /*ejecutar todo*/
    $("#btnAbrirNCategoria").click(() => {
        $("#modalCategoria").modal("show");
        $("#exampleModalLabel").html("Registrar Categoria");
    });
    //peticion
    procesarAjaxCategoria();
})


function procesarAjaxCategoria() {
    //var datosSerializadosCompletos = $("#"+ $("#nameFromCategoria")).serialize();
    var datosSerializadosCompletos = $("#nameFromCategoria").serialize();
    if ($('#nameFormCategoria').val().toLowerCase() !== "FrmCategoria") {
        datosSerializadosCompletos += "txtNombreCategoria=" + $("#txtNombreCategoria").val();
    }
    datosSerializadosCompletos += "&numberPageCategoria=" + $("#numberPageCategoria").val();
    datosSerializadosCompletos += "&sizePageCategoria=" + $("#sizePageCategoria").val();
    datosSerializadosCompletos += "&action=" + $("#actionPageCategoria").val();

    console.log(datosSerializadosCompletos);

    $.ajax({
        url: "categorias",
        type: "POST",
        data: datosSerializadosCompletos,
        dataType: "json",
        success: function (jsonRespose) {
            console.log(jsonRespose);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error de llamada ajax " + " jqXHR " +jqXHR + " errorThrown "+errorThrown);
        }
    });

}

