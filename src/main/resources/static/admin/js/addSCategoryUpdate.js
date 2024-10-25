// Inputu silən funksiya (ilk inputu silməmək şərti ilə)
function removeSubcategoryUpdate(button) {
    const container = document.getElementById('subcategoriesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove(); // Inputu sil
    } else {
        alert("You cannot remove the last subcategory!");
    }
}

// Yeni alt kateqoriya inputu əlavə edən funksiya
function addSubcategoryUpdate() {
    const container = document.getElementById('subcategoriesContainer');

    const inputWrapper = document.createElement('div');
    inputWrapper.style.marginBottom = '10px';
    inputWrapper.style.display = 'flex';
    inputWrapper.style.alignItems = 'center';

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.name = 'subcategoriesName'; // Eyni adla siyahı kimi qəbul edilir
    newInput.placeholder = 'Additional subcategory';
    newInput.style.width = '350px';
    newInput.style.marginRight = '5px';

    // Sil düyməsi əlavə etmək
    const removeButton = document.createElement('button');
    removeButton.innerText = 'Remove';
    removeButton.type = 'button';
    removeButton.onclick = function() {
        removeSubcategoryUpdate(this);
    };

    // Inputu və sil düyməsini div-ə əlavə etmək
    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Form göndərilməzdən öncə inputları yoxlayan və formu doğrulayan funksiya
function validateAndSubmitFormUpdate() {
    const container = document.getElementById('subcategoriesContainer');
    const inputs = container.querySelectorAll('input[name="subcategoriesName"]');

    let isValid = true;

    inputs.forEach(input => {
        // input.value yoxlanılacaq, bu subcategoryName.name-ə istinad etməlidir
        if (input.value.trim() === '') {
            if (container.children.length > 1) {
                input.parentElement.remove(); // Boş inputları sil
            } else {
                alert("The first subcategory cannot be empty.");
                isValid = false;
            }
        }
    });

    return isValid; // Əgər bütün inputlar keçərli olsa, form təsdiqlənəcək
}
