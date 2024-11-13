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
            deleteBtn.onclick = function() { imgContainer.remove(); };

            imgContainer.appendChild(img);
            imgContainer.appendChild(deleteBtn);
            imageContainer.appendChild(imgContainer);

            imgContainer.onmouseover = function() { deleteBtn.style.display = "block"; };
            imgContainer.onmouseout = function() { deleteBtn.style.display = "none"; };
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

    if (imageCount === 0) {
        imageWarning.style.display = 'block'; // Xəbərdarlığı göstərir
        return false; // Formun təqdim edilməsini dayandırır
    }

    imageWarning.style.display = 'none'; // Əgər şəkil əlavə edilibsə, xəbərdarlığı gizlədir
    return true;
}

