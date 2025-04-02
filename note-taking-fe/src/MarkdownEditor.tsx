import { useEffect, useState } from 'react'
import MDEditor from '@uiw/react-md-editor'
import './markdown-editor.css'

interface MarkdownEditorProps {
  content: string;
  onChange: (content: string) => void;
}

const MarkdownEditor: React.FC<MarkdownEditorProps> = ({ content, onChange }) => {
  const [markdownContent, setMarkdownContent] = useState(content);

  // Update local state only when content prop changes
  useEffect(() => {
    setMarkdownContent(content);
  }, [content]);

  return (
    <div className="markdown-editor">
      <MDEditor
        value={markdownContent}
        onChange={(value) => {
          setMarkdownContent(value || '');
          onChange(value || '');
        }}
        preview="edit"
      />
    </div>
  )
}

export default MarkdownEditor; 