document.addEventListener('DOMContentLoaded', function ()
{
    const tagContainer = document.getElementById('tags-container');
    const tagHiddenInput = document.getElementById('tag-hidden-input');
    const tagInput = document.getElementById('tag-input');
    const tags = new Set();

    if (!tagContainer || !tagInput || !tagHiddenInput) return;

    // 게시글 수정시 서버에서 전송된 tagHiddenInput 태그 정보를 tagInput에 등록
    if (tagHiddenInput.value)
    {
        tagHiddenInput.value
            .split(',')
            .map(tag => tag.trim())
            .filter(Boolean)
            .forEach(addTag);
    }

    // tagHiddenInput 업데이트
    function updateHiddenInput()
    {
        const tagsArray = Array.from(tags);
        tagHiddenInput.value = tagsArray.length > 0 ? tagsArray.join(',') : '';
    }

    // 엔터를 누를 때, 태그 정보 입력
    tagInput.addEventListener('keydown', function (event)
    {
        if (event.key === 'Enter')
        {
            event.preventDefault();
            handleTagInput(tagInput.value);
        }
    });

    // 스페이스바를 누를 때, 태그 정보 입력
    tagInput.addEventListener('input', function (event)
    {
        const value = event.target.value;
        if (value.includes(' '))
        {
            handleTagInput(value.replace(' ', ''));
        }
    });

    // 태그 입력 처리
    function handleTagInput(tagText)
    {
        tagText = tagText.trim();

        if (tagText === '') return;
        if (tags.has(tagText))
        {
            alert('이미 존재하는 태그입니다.');
            return;
        }
        addTag(tagText);
        tagInput.value = '';
    }

    tagContainer.addEventListener('click', () =>
    {
        tagInput.focus();
    });

    function addTag(tagText)
    {
        tags.add(tagText);

        // 태그 뱃지 생성
        const tag = document.createElement('span');
        tag.classList.add('badge', 'bg-primary', 'me-1', 'mb-1', 'd-flex', 'align-items-center');
        tag.textContent = tagText;

        // 태그 삭제 버튼 생성
        const removeButton = document.createElement('button');
        removeButton.type = 'button';
        removeButton.classList.add('btn-close', 'btn-close-white', 'ms-2');
        removeButton.setAttribute('aria-label', 'Remove');
        removeButton.style.fontSize = '0.7em';

        // 삭제 버튼 클릭 시
        removeButton.onclick = () =>
        {
            tags.delete(tagText);  // Set에서 제거
            tag.remove();       // DOM에서 제거
            updateHiddenInput();// hidden input 업데이트
        };

        tag.appendChild(removeButton);
        tagContainer.insertBefore(tag, tagInput);
        tagInput.value = '';

        updateHiddenInput();
    }
});