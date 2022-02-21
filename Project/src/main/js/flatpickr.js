import flatpickr from "flatpickr";

const establishmentDatePicker = document.getElementById("establishmentDate");

flatpickr(establishmentDatePicker,{
    enableTime: false,
    altInput: true,
    altFormat: 'd M Y',
    dateFormat: 'Y-m-d'
});