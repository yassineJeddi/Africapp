/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            allNextBtn = $('.nextBtn');

    allWells.hide();

    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')), $item = $(this);

        if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-danger').addClass('btn-default');
            $item.addClass('btn-danger');
            allWells.hide();
            $target.show();
            $target.find('.input:eq(0)').focus();
        }
    });

    allNextBtn.click(function () {

        isValid = true;
        var select = $('#parc_rombundle_ordremission_ville option:selected');
        if (select.text().length === 0) {
            isValid = false;
            select.closest(".form-group").addClass("has-error");
        }
        var curStep = $(this).closest(".setup-content"),
                curStepBtn = curStep.attr("id"),
                nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
                curInputs = curStep.find("input[type='text']"),
                isValid = true;
        $(".form-group").removeClass("has-error");
        for (var i = 0; i < curInputs.length; i++) {
            if (!curInputs[i].validity.valid) {
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }

        if (isValid)
            nextStepWizard.removeAttr('disabled').trigger('click');
    });

    $('div.setup-panel div a.btn-danger').trigger('click');
});