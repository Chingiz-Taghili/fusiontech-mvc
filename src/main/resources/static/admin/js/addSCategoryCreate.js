// Dinamik şəkildə alt kateqoriya inputu əlavə edən funksiya (CREATE)
function addSubcategoryCreate() {
    const container = document.getElementById('SubcategoriesContainer');

    const inputWrapper = document.createElement('div');
    inputWrapper.style.display = 'flex';
    inputWrapper.style.alignItems = 'center';
    inputWrapper.style.marginBottom = '10px';

    const newInput = document.createElement('input');
    newInput.type = 'text';
    newInput.name = 'subcategoryNames';
    newInput.placeholder = 'Additional subcategory';
    newInput.style.width = '350px';
    newInput.style.marginRight = '5px';

    const removeButton = document.createElement('button');
    removeButton.innerText = 'Remove';
    removeButton.type = 'button';
    removeButton.onclick = function () {
        removeSubcategoryCreate(this);
    };

    inputWrapper.appendChild(newInput);
    inputWrapper.appendChild(removeButton);
    container.appendChild(inputWrapper);
}

// Inputu silən funksiya (CREATE)
function removeSubcategoryCreate(button) {
    const container = document.getElementById('SubcategoriesContainer');
    if (container.children.length > 1) {
        button.parentElement.remove();
    } else {
        alert("You cannot remove the last subcategory!");
    }
}

// Form göndərilməzdən əvvəl yoxlama funksiyası (CREATE)
function validateAndSubmitFormCreate() {
    const container = document.getElementById('SubcategoriesContainer');
    const inputs = container.querySelectorAll('input[name="subcategoryNames"]');

    let isValid = true;

    inputs.forEach(input => {
        if (input.value.trim() === '') {
            if (container.children.length > 1) {
                input.parentElement.remove();
            } else {
                alert("The first subcategory cannot be empty.");
                isValid = false;
            }
        }
    });

    return isValid;
}
