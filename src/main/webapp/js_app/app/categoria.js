$(document).ready(function () {
    /*ejecutar todo*/
    $("#btnAbrirNCategoria").click(() => {
        $('#txtIdCategoriaER').val("");
        $('#txtNombreCategoriaER').val("");
        $('.error-validation').fadeOut();
        $('#actionCategoria').val("addCategoria");
        $('#tituloModalManCategoria').html("REGISTRAR CATEGORIA");
        $('#ventanaModalManCategoria').modal('show');
    });
    
    $('#FrmCategoria').submit(function () {
        $('#actionCategoria').val("paginarCategoria");
        $('#nameFormCategoria').val("FrmCategoria");
        $('#numberPageCategoria').val("1");
        $('#modalCargandoCategoria').modal('show');
        //procesarAjaxCategoria();
        return false;
    });

    $('#FrmCategoriaModal').submit(function () {
        if (validarFormularioCategoria()) {
            $('#nameFormCategoria').val("FrmCategoriaModal");
            $('#modalCargandoCategoria').modal('show');
        }
        return false;
    });

    addEventoCombosPaginar();
    addValicacionesCamposCategoria();
    //peticion
    $("#modalCargandoCategoria").on("show.bs.modal", function () {
        procesarAjaxCategoria();
    });
    
    $("#ventanaModalManCategoria").on('hidden.bs.modal', function () {
        $("#actionCategoria").val("paginarCategoria");
    });
    
    $("#modalCargandoCategoria").modal("show");

})


function procesarAjaxCategoria() {
    var datosSerializadosCompletos = $('#' + $('#nameFormCategoria').val()).serialize();
    //var datosSerializadosCompletos = $("#nameFromCategoria").serialize();
    if ($('#nameFormCategoria').val().toLowerCase() !== "Frmcategoria") {
        datosSerializadosCompletos += "&txtNombreCategoria=" + $("#txtNombreCategoria").val();
    }
    datosSerializadosCompletos += "&numberPageCategoria=" + $("#numberPageCategoria").val();
    datosSerializadosCompletos += "&sizePageCategoria=" + $("#sizePageCategoria").val();
    datosSerializadosCompletos += "&action=" + $("#actionCategoria").val();

    console.log(datosSerializadosCompletos);

    $.ajax({
        url: "categorias",
        type: "POST",
        data: datosSerializadosCompletos,
        dataType: "json",
        success: function (jsonResponse) {
            $('#modalCargandoCategoria').modal("hide");
            if ($('#actionCategoria').val().toLowerCase() === "paginarcategoria") {
                listarCategoria(jsonResponse.BEAN_PAGINATION);
            } else {
                if (jsonResponse.MESSAGE_SERVER.toLowerCase() === "ok") {
                    viewAlert('success', getMessageServerTransaction($('#actionCategoria').val(), 'Categoria', 'a'));
                    listarCategoria(jsonResponse.BEAN_PAGINATION);
                } else {
                    viewAlert('warning', jsonResponse.MESSAGE_SERVER);
                }
            }
            $("#ventanaModalManCategoria").modal("hide");
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $('#modalCargandoCategoria').modal("hide");
            $("#ventanaModalManCategoria").modal("hide");
            viewAlert('error', 'Error interno en el Servidor');
            console.log("Error de llamada ajax " + " jqXHR " + jqXHR + " errorThrown " + errorThrown);
        }
    });

}

function listarCategoria( BEAN_PAGINATION) {
    var $pagination = $("#paginationCategoria");
    $("#tbodyCategoria").empty();
    $pagination.twbsPagination("destroy");
    $("#nameCrudCategoria").html("[" + BEAN_PAGINATION.COUNT_FILTER + "] CATEGORIAS");
    if (BEAN_PAGINATION.COUNT_FILTER > 0) {

        var fila;
        var atributos;
        $(BEAN_PAGINATION.LIST).each(function (index, value) {
            //console.log(index, value);
            fila = "<tr ";
            atributos = "idcategoria='" + value.idcategoria + "'";
            atributos += "nombre='" + value.nombre + "'";
            fila += atributos;
            fila += ">";
            fila += "<td>" + value.nombre + "</td>";
            fila += "<td class='text-center'><button class='btn btn-secondary btn-xs editar-categoria'><i class='fa fa-edit'></i></button></td>";
            fila += "<td class='text-center'><button class='btn btn-secondary btn-xs eliminar-categoria'><i class='fa fa-trash'></i></button></td>";
            fila += "</tr>";
            $("#tbodyCategoria").append(fila);
            //botones de la paginacion
            var defaultOptions = getDefaultOptionsPagination();
            var options = getOptionsPagination(BEAN_PAGINATION.COUNT_FILTER,
                    $('#sizePageCategoria'),
                    $('#numberPageCategoria'),
                    $('#actionCategoria'),
                    'paginarCategoria',
                    $('#nameForm'),
                    'FrmCategoria',
                    $('#modalCargandoCategoria'));
            $pagination.twbsPagination($.extend({}, defaultOptions, options));

        });
    } else {
        console.log("No existen registros filtrados");
        viewAlert('warning', 'No se enconntraron resultados');
}
}


function addValicacionesCamposCategoria() {
    $('#txtNombreCategoriaER').on('change', function () {
        $(this).val() === ""
                ? $("#validarNombreCategoriaER").fadeIn("slow")
                : $("#validarNombreCategoriaER").fadeOut();
    });
}

function validarFormularioCategoria() {
    if ($('#txtNombreCategoriaER').val() === "") {
        $("#validarNombreCategoriaER").fadeIn("slow");
        return false;
    } else {
        $("#validarNombreCategoriaER").fadeOut();
    }
    return true;
}