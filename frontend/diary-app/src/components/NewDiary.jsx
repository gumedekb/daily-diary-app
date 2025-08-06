import { useState } from "react";
import api from "../api";

export default function NewDiary({ onAdd }) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [error, setError] = useState(null);

   const handleAdd = async (e) => {
      e.preventDefault();
      try {
         const response = await api.post("/diary", { title, content });
         setTitle("");
         setContent("");
         if (onAdd) {
            onAdd(response.data);  // add new diary entry to parent state
         }
         else {
            setError("Diary not set");
         }
      } catch (err) {
         setError("Failed to add diary.");
      }
   };

  return (
    <form
      onSubmit={handleAdd}
      className="bg-gray-800 p-6 rounded shadow space-y-4"
    >
      <h3 className="text-lg font-bold">New Diary Entry</h3>
      {error && <div className="text-red-400">{error}</div>}
      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        className="w-full p-2 bg-gray-700 rounded"
        required
      />
      <textarea
        placeholder="Content"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        rows={4}
        className="w-full p-2 bg-gray-700 rounded"
        required
      />
      <button type="submit" className="bg-indigo-600 px-4 py-2 rounded hover:bg-indigo-500">
        Add Entry
      </button>
    </form>
  );
}
