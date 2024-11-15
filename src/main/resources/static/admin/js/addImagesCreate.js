// Yeni şəkilləri saxlayan array
let images = [];

// Şəkil əlavə edildikdə `images` array-ə şəkil fayllarını əlavə edirik
document.getElementById("imageUpload").onchange = function() {
    const imageContainer = document.getElementById("imagePreviewContainer");
    const noImageText = document.getElementById("noImageText");
    noImageText.style.display = 'none';

    Array.from(this.files).forEach(file => {
        const reader = new FileReader();
        reader.onload = function(e) {
            const imgContainer = document.createElement("div");
            imgContainer.style.position = "relative";
            imgContainer.style.display = "inline-block";
            imgContainer.style.flex = "0 0 19%";

            const img = document.createElement("img");
            img.src = e.target.result;
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.borderRadius = "5px";
            img.style.objectFit = "cover";
            img.style.aspectRatio = "1";
            img.onclick = function() { openModal(e.target.result); }; // Modal açmaq üçün

            const deleteBtn = document.createElement("button");
            deleteBtn.innerHTML = "X";
            deleteBtn.style.position = "absolute";
            deleteBtn.style.top = "5px";
            deleteBtn.style.right = "5px";
            deleteBtn.style.backgroundColor = "red";
            deleteBtn.style.color = "white";
            deleteBtn.style.border = "none";
            deleteBtn.style.borderRadius = "50%";
            deleteBtn.style.width = "20px";
            deleteBtn.style.height = "20px";
            deleteBtn.style.fontSize = "12px";
            deleteBtn.style.cursor = "pointer";
            deleteBtn.style.display = "none";
            deleteBtn.onclick = function() {
                imgContainer.remove();
                // Silinən şəkili images array-dən silirik
                const index = images.indexOf(file);
                if (index > -1) {
                    images.splice(index, 1); // Array-dən silirik
                }
                // Gizli inputu da silirik
                const hiddenInput = document.querySelector(`input[data-id="${file.name}"]`);
                if (hiddenInput) {
                    hiddenInput.remove();
                }
            };

            imgContainer.appendChild(img);
            imgContainer.appendChild(deleteBtn);
            imageContainer.appendChild(imgContainer);

            imgContainer.onmouseover = function() { deleteBtn.style.display = "block"; };
            imgContainer.onmouseout = function() { deleteBtn.style.display = "none"; };

            // Yeni inputu gizli div-ə əlavə edirik
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "file";
            hiddenInput.name = "images"; // Controller-də "images" adında qəbul ediləcək
            hiddenInput.style.display = "none";
            hiddenInput.setAttribute("data-id", file.name); // Şəkilin adını atributda saxlayırıq

            // Şəkil faylını gizli inputa mənimsədirik
            const dataTransfer = new DataTransfer();
            dataTransfer.items.add(file);
            hiddenInput.files = dataTransfer.files;

            document.getElementById('hiddenInputsContainer').appendChild(hiddenInput);

            // `images` array-ə əlavə edirik
            images.push(file);
        };
        reader.readAsDataURL(file);
    });
};

// Modal açma funksiyası
function openModal(imageSrc) {
    const modal = document.getElementById("imageModal");
    const modalImage = document.getElementById("modalImage");
    modalImage.src = imageSrc;
    modal.style.display = "flex";
}

// Modalı bağlama funksiyası
function closeModal() {
    const modal = document.getElementById("imageModal");
    modal.style.display = "none";
}

// Şəkil əlavə edilmədiyi halda formu təqdim etməyə mane olur
function validateAndSubmitForm() {
    const imageInput = document.getElementById('imageUpload');
    const imageWarning = document.getElementById('imageWarning');
    const imageCount = imageInput.files.length;

    // Gizli input-larda şəkil olub-olmaması yoxlanılır
    const hiddenInputs = document.querySelectorAll('input[name="images"]');
    const hiddenInputCount = hiddenInputs.length;

    if (imageCount === 0 && hiddenInputCount === 0) { // `images` array də boşdursa xəbərdarlıq göstərir
        imageWarning.style.display = 'block'; // Xəbərdarlığı göstərir
        return false; // Formun təqdim edilməsini dayandırır
    }

    imageWarning.style.display = 'none'; // Əgər şəkil əlavə edilibsə, xəbərdarlığı gizlədir
    return true;
}
