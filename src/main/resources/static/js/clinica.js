// modal
const NovaConsulta = document.getElementById('btnNovaConsulta');
const modal = document.querySelector("dialog");


const abrirModal = function(){
    modal.showModal(); //posso desabilitar com ESC
    // modal.show();//o eSC não funciona para fechar
}

NovaConsulta.onclick=abrirModal();

const buttonClose = document.getElementById('fechar')
buttonClose.onclick=function(){
    modal.close();
}
// fim modal

//função que volta uma tela.
function voltarTela() {
    window.history.back();
}