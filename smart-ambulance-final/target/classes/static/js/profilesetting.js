function toggleEdit(section) {
const personalFields = ['fullName', 'email', 'phone', 'dob', 'address', 'city'];
const medicalFields = ['bloodType'];
let fields = section === 'personal' ? personalFields : medicalFields;

const isDisabled = document.getElementById(fields[0]).disabled;

fields.forEach(id => {
document.getElementById(id).disabled = !isDisabled;
});

if (!isDisabled) {
showSuccessMessage();
}
}

function showSuccessMessage() {
const msg = document.getElementById('successMessage');
msg.classList.add('show');
setTimeout(() => {
msg.classList.remove('show');
}, 3000);
}
