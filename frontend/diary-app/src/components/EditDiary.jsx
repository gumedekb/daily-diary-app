import { useState } from "react";

const  EditDiary = ({ entry, onCancel, onUpdate }) => {
   const [title, setTitle] = useState(entry.title);
   const [content, setContent] = useState(entry.content);
   const [error, setError] = useState(null);

   const handleSubmit = (e) => {
      e.preventDefault();
      if (!title.trim() || !content.trim()) {
         setError("Title and content are required.");
         return;
      }

      onUpdate({
         ...entry,
         title,
         content,
      });
   };

   return (
      <form onSubmit={handleSubmit} className="bg-gray-800 p-6 rounded shadow space-y-4">
         <h3 className="text-lg font-bold">Edit Entry</h3>
         {error && <div className="text-red-400">{error}</div>}
         <input
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full p-2 bg-gray-700 rounded"
         />
         <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            rows={4}
            className="w-full p-2 bg-gray-700 rounded"
         />
         <div className="flex space-x-2">
            <button type="submit" className="bg-green-600 px-4 py-2 rounded hover:bg-green-500">
               Save Changes
            </button>
            <button type="button" onClick={onCancel} className="bg-gray-600 px-4 py-2 rounded hover:bg-gray-500">
               Cancel
            </button>
         </div>
      </form>
   );
}

export default EditDiary;
