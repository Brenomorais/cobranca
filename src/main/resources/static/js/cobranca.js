$('#confirmacaoExclusaoModal').on(
		'show.bs.modal',
		function(event) {

			var button = $(event.relatedTarget);
			var codigoTitulo = button.data('codigo');
			var descricaoTitulo = button.data('descricao');

			/*
			 * Teste : alert(codigoTitulo); /* Se a url estiver terminando com /
			 * concatena o codigo Se não tiver terminando com / adiciona o
			 * codigo é a barra
			 */

			var modal = $(this);
			var form = modal.find('form');
			var action = form.data('url-base');
			if (!action.endsWith('/')) {
				action += '/';
			}
			form.attr('action', action + codigoTitulo);

			modal.find('.modal-body span').html(
					'Tem que certexa que deseja excluir o título: <strong>'
							+ descricaoTitulo + '</strong> ?');
		});

$(function() {
	$('[rel="tooltip"]').tooltip();

	$('.js-currency').maskMoney({
		decimal : ',',
		thousands : '.',
		allowZero : true
	});

	/**/
	$('.js-atualizar-status').on('click', function(event) {
		event.preventDefault();

		// capturando botao recber
		var botaoReceber = $(event.currentTarget);
		// url que recebe url link do titulo
		var urlReceber = botaoReceber.attr('href');

		var response = $.ajax({
			url : urlReceber,
			type : 'PUT'
		});

		response.done(function(e) {
			var codigoTitulo = botaoReceber.data('codigo');
			$('[data-role=' +codigoTitulo+']').html('<span class="label label-success">' + e + '</span>');
			botaoReceber.hide();
		});

		response.fail(function(e) {
			console.log(e);
			alert("Erro ao quitar cobrança.");
		});

	});
});