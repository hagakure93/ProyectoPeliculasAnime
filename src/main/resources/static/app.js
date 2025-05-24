document.addEventListener('DOMContentLoaded', () => {
    const grid = document.getElementById('animeGrid');
    const detailView = document.getElementById('detailView');
    const detailImage = document.getElementById('detailImage');
    const detailTitle = document.getElementById('detailTitle');
    const detailDescription = document.getElementById('detailDescription');
    const showCommentsBtn = document.getElementById('showCommentsBtn');
    const commentsView = document.getElementById('commentsView');
    const commentsList = document.getElementById('commentsList');
    const closeCommentsBtn = document.getElementById('closeCommentsBtn');
    const closeDetailBtn = document.getElementById('closeDetailBtn');
    const addCommentForm = document.getElementById('addCommentForm');
    const commentInput = document.getElementById('commentInput');

    let mangas = [];

    // 1. Obtener los datos desde la API REST
    fetch('/api/movies')
        .then(response => response.json())
        .then(data => {
            mangas = data;
            renderMangas();
        })
        .catch(error => {
            grid.innerHTML = "<p style='color:red'>Error cargando mangas.</p>";
            console.error(error);
        });

    // 2. Renderizar los mangas
    function renderMangas() {
        grid.innerHTML = '';
        mangas.forEach(manga => {
            const div = document.createElement('div');
            div.className = 'grid-item';
            const img = document.createElement('img');
            img.src = manga.imageUrl;
            img.alt = manga.title;
            img.setAttribute('data-id', manga.id);
            img.className = 'manga-img';
            img.addEventListener('click', function() {
                detailImage.innerHTML = `<img src="${manga.imageUrl}" alt="${manga.title}">`;
                detailTitle.textContent = manga.title;
                detailDescription.textContent = manga.description;
                detailView.classList.remove('hidden');
                showCommentsBtn.setAttribute('data-id', manga.id);
            });
            div.appendChild(img);
            grid.appendChild(div);
        });
    }

    // 3. Cerrar detalles
    closeDetailBtn.addEventListener('click', function() {
        detailView.classList.add('hidden');
    });

    // 4. Mostrar comentarios (si tu API los devuelve, si no, puedes hacer otro fetch aquí)
    showCommentsBtn.addEventListener('click', function() {
        const movieId = this.getAttribute('data-id');
        fetch(`/api/movies/${movieId}/comments`)
            .then(response => response.json())
            .then(comments => {
                commentsList.innerHTML = comments.length > 0
                    ? comments.map(c => `<div class="comment">${c.text}</div>`).join('')
                    : "<div class='comment'>Sin comentarios.</div>";
                commentsView.classList.remove('hidden');
            })
            .catch(error => {
                commentsList.innerHTML = "<div class='comment'>Error cargando comentarios.</div>";
                commentsView.classList.remove('hidden');
            });
    });

    // 5. Cerrar comentarios
    closeCommentsBtn.addEventListener('click', function() {
        commentsView.classList.add('hidden');
    });

    // 6. Agregar comentario
    addCommentForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const movieId = showCommentsBtn.getAttribute('data-id');
        const commentText = commentInput.value.trim();
        if (!commentText) return;

        fetch(`/api/movies/${movieId}/comments`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ text: commentText }) // usa 'text'
        })
        .then(response => response.json())
        .then(newComment => {
            // Recarga los comentarios
            showCommentsBtn.click();
            commentInput.value = '';
        });
    });
});