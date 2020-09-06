function getDefaultOptionsPagination(){
    
    var defaultOptions = {
        startPage : 1,
        totalPages: 10,
        visiblePages: 5,
        initiateStartPageClick: false,
        first : "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
        prev  : "<i class='fa fa-angle-left' aria-hidden='true'></i>",
        next  : "<i class='fa fa-angle-right' aria-hidden='true'></i>",
        last  : "<i class='fa fa-angle-double-right' aria-hidden='true'></i>"
    }
    
    return defaultOptions;
}

function getTotalPages(count_filter, size_page){   
    
    var count_pages = count_filter / size_page;

    if (count_filter % size_page > 0) {
        count_pages += 1;
    }
    return count_pages;
}

function getOptionsPagination(count_filter, $sizePage, $numberPage, $action, valueAction, $form, valueForm, $modalLoanding) {
    
    var totalPages = getTotalPages(count_filter, parseInt($sizePage.val()));
    
    var options = {
        startPage : parseInt($numberPage.val()),
        totalPages: totalPages,
        visiblePages:5,
        initiateStartPageClick:false,
        first: "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
        prev: "<i class='fa fa-angle-left' aria-hidden='true'></i>",
        next: "<i class='fa fa-angle-right' aria-hidden='true'></i>",
        last: "<i class='fa fa-angle-double-right' aria-hidden='true'></i>",
        onPageClick : function(evt, page){
            //console.log(page)
            $action.val(valueAction);
            $numberPage.val(page);
            $form.val(valueForm);
            $modalLoanding.modal("show");
        }
    }
    return options;
}

function addEventoCombosPaginar() {
    $('.combo-paginar').on('change', function () {
        $("#" + $(this).attr('idBtnBuscar')).trigger("click");
    });
    /*
    $("#sizePageCategoria").on("change", function () {
        $("#btnBuscarCategoria").trigger("click");
    })*/
}


function viewAlert(type, message) {
    swal({
        title: "MyApp",
        text: message,
        /*
         html : "<img src='/sisbu/img/logo_ceplomo.png' alt='Logo' style='width:150px;'><br><h5>"
         + message + "</h5>",*/
        type: type,
        showCancelButton: false,
        confirmButtonText: "Aceptar",
        confirmButtonClass: 'btn btn-primary',
        buttonsStyling: false
    });
    $('.swal2-icon').css("margin-bottom", "20px");
}

function getMessageServerTransaction(action, entidad, delimitador_sexo) {
    var messageOut = entidad;
    var operation = "";
    for (var i = 0; i < 3; i++) {
        operation += action.substring(i, i + 1);
    }
    switch (operation) {
        case "add":
            messageOut += " registrad" + delimitador_sexo + " exitosamente!";
            break;
        case "upd":
            messageOut += " modificad" + delimitador_sexo + " exitosamente!";
            break;
        case "del":
            messageOut += " eliminad" + delimitador_sexo + " exitosamente!";
            break;
    }
    return messageOut;
}