COLDIGO.marca = new Object();

$(document).ready(function() {
	
	
	COLDIGO.marca.cadastrar = function() {

		var marca = new Object();
		marca.nome = document.frmAddMarca.nome.value;

		if (marca.nome == "") {
			COLDIGO.exibirAviso("Preencha o campo nome!")
		} else {
			$.ajax({
				type: "POST",
				url: COLDIGO.PATH + "marca/inserir",
				data: JSON.stringify(marca),
			
				success: function(msg) {
					COLDIGO.exibirAviso(msg);
					$("#addMarca").trigger("reset");
				},
				error: function(info) {
					COLDIGO.exibirAviso("Erro ao cadastrar uma nova marca: " + info.status + " . " + info.statusText);
				}
			});
		}
	};

	COLDIGO.marca.exibir = function(listaMarcas) {
	    var tabelaMarca = "<table>" +
	        "<tr>" +
	        "<th>ID</th>" +
	        "<th>Marca</th>" +
	        "<th class='acoes'>Ações</th>" +
	        "</tr>";
	
	    if (listaMarcas != undefined && listaMarcas.length > 0) {
	        for (var i = 0; i < listaMarcas.length; i++) {
	            tabelaMarca += "<tr>" +
	                "<td>" + listaMarcas[i].id + "</td>" +
	                "<td>" + listaMarcas[i].nome + "</td>" +
	                "<td>" +
	                "<a onclick=\"COLDIGO.marca.exibirEdicao('" + listaMarcas[i].id + "')\"><img src='../../imgs/edit.png' alt='Editar registro'></a>" +
	                "<a onclick=\"COLDIGO.marca.excluir('" + listaMarcas[i].id + "')\"><img src='../../imgs/delete.png' alt='Excluir registro'></a>" +
	                "</td>" +
	                "</tr>";
	        }
	    } else if (listaMarcas == "") {
	        tabelaMarca += "<tr><td colspan='3'> Nenhum registro encontrado</td ></tr > ";
	    }
	    tabelaMarca += "</table>";
	
	    return tabelaMarca;
};


	COLDIGO.marca.buscarPorNome = function() {
		
		var valorBusca = $("#campoBuscaMarca").val();
		
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscarPorNome",
			data: "valorBusca=" + valorBusca,
			success: function(dadosMarca) {
				
				dadosMarca = JSON.parse(dadosMarca);
				$("#listaMarcas").html(COLDIGO.marca.exibir(dadosMarca));
			},
			error: function(info) {
				COLDIGO.exibirAviso("Erro ao consultar os contatos: " + info.status + " - " + info.statusText);
			}
		});
	};
	COLDIGO.marca.buscarPorNome();
	
	COLDIGO.marca.excluir = function(id) {
	console.log("ID para excluir: " + id); 
	
	
	$.ajax({
		type: "DELETE",
		url: COLDIGO.PATH + `marca/excluir/${id}`,
		success: function(msg) {
			console.log("Eliminación exitosa: " + msg); 
			COLDIGO.exibirAviso(msg);
			COLDIGO.marca.buscarPorNome();
		},
		error: function(info) {
			console.log("Error al eliminar: " + info.status + " - " + info.statusText); 
			COLDIGO.exibirAviso("Erro ao excluir: " + info.status + " - " + info.statusText);
		}
	});
};


	COLDIGO.marca.exibirEdicao = function(id) {
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscarPorId",
			data: "id=" + id,
			success: function(marca) {
				document.frmEditaMarca.idMarca.value = marca.id;
				document.frmEditaMarca.nome.value = marca.nome;

				var modalEditaMarca = {
					title: "Editar Marca",
					height: 400,
					width: 550,
					modal: true,
					buttons: {
						"Salvar": function() {
							COLDIGO.marca.editar();
						},
						"Cancelar": function() {
							$(this).dialog("close");
						},
					}
				};
				$("#modalEditaMarca").dialog(modalEditaMarca);
			},
			error: function(info) {
				COLDIGO.exibirAviso("Erro ao buscar  para edição: " + info.status + " - " + info.statusText);
			}
		});
	};

	COLDIGO.marca.editar = function() {

		var marca = new Object();
		marca.id = document.frmEditaMarca.idMarca.value;
		marca.nome = document.frmEditaMarca.nome.value;
		
		$.ajax({
			type: "PUT",
			url: COLDIGO.PATH + "marca/alterar",
			data:JSON.stringify(marca),
			success: function(msg) {
				COLDIGO.exibirAviso(msg);
				COLDIGO.marca.buscarPorNome();
				$("#modalEditaMarca").dialog("close");
			},
			error: function(info) {
				COLDIGO.exibirAviso("Erro ao editar marca: " + info.status + " - " + info.statusText);
			}
		});

	};


	
});