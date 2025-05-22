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

    let currentAnimeId = null;

    // Mostrar detalle al hacer click en una portada
    grid.addEventListener('click', (e) => {
        const item = e.target.closest('.grid-item');
        if (!item) return;

        // Agranda la imagen y muestra detalles
        const imgSrc = item.querySelector('img').src;
        detailImage.innerHTML = `<img src="${imgSrc}" alt="${item.dataset.title}">`;
        detailTitle.textContent = item.dataset.title;
        detailDescription.textContent = item.dataset.description;
        detailView.classList.remove('hidden');
        currentAnimeId = item.dataset.id;
    });

    // Mostrar comentarios al pulsar el botón
    showCommentsBtn.addEventListener('click', () => {
        detailView.classList.add('hidden');
        commentsView.classList.remove('hidden');
        commentsList.innerHTML = '<div>Cargando comentarios...</div>';

        // Petición AJAX a tu API para obtener los comentarios reales
        fetch(`/api/movies/${currentAnimeId}/comments`, {
            headers: {
                'Authorization': 'Basic ' + btoa('hagakure:nuevamysqlclavE6.')
            }
        })
            .then(res => {
                if (!res.ok) throw new Error('Error al cargar comentarios');
                return res.json();
            })
            .then(comments => {
                if (comments.length === 0) {
                    commentsList.innerHTML = '<div>No hay comentarios aún.</div>';
                } else {
                    commentsList.innerHTML = comments.map(c =>
                        `<div class="comment">
                            <strong>${c.author ? c.author : 'Anónimo'}:</strong>
                            <span>${c.content}</span>
                        </div>`
                    ).join('');
                }
            })
            .catch(() => {
                commentsList.innerHTML = '<div>Error al cargar comentarios.</div>';
            });
    });

    // Cerrar comentarios
    closeCommentsBtn.addEventListener('click', () => {
        commentsView.classList.add('hidden');
    });

    // Opcional: cerrar el detalle al hacer click fuera
    window.addEventListener('click', (e) => {
        if (e.target === detailView) {
            detailView.classList.add('hidden');
        }
        if (e.target === commentsView) {
            commentsView.classList.add('hidden');
        }
    });
});