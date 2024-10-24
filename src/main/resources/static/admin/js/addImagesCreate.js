// Dinamik şəkildə şəkil URL-i əlavə edən funksiya
function addImage() {
    const container = document.getElementById('imagesContainer');

    const inputWrapper = document.createElement('div');
    inputWrapper.style.display = 'flex';
    inputWrapper.style.alignItems = 'center';
    inputWrapper.style.marginBottom = '10px';

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.name = 'images'; // Eyni adla siyahı kimi qəbul edilir
    newInput.placeholder = 'Additional image';
    newInput.style.width = '350px';
    newInput.style.marginRight = '5px';

    // Sil düyməsi əlavə etmək
    const removeButton = document.createElement('button');
    removeButton.innerText = 'Remove';
    removeButton.type = 'button';
    removeButton.onclick = function() {
        removeImage(this);
    };

    // Inputu və sil düyməsini div-ə əlavə etmək
    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Inputu silən funksiya (ilk inputu silməmək şərti ilə)
function removeImage(button) {
    const container = document.getElementById('imagesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove(); // Inputu sil
    } else {
        alert("You cannot remove the last image input!");
    }
}

// Form göndərilməzdən əvvəl boş inputları yoxlayıb silən və formu doğrulayan funksiya
function validateAndSubmitForm() {
    const container = document.getElementById('imagesContainer');
    const inputs = container.querySelectorAll('input[name="images"]');

    let isValid = true;

    inputs.forEach(input => {
        if (input.value.trim() === '') {
            if (container.children.length > 1) {
                input.parentElement.remove(); // Boş inputları sil
            } else {
                alert("The first image URL cannot be empty.");
                isValid = false;
            }
        }
    });

    return isValid; // Əgər bütün inputlar keçərli olsa, form təsdiqlənəcək
}
