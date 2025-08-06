import { useState, useEffect } from "react";
import api from "../api";
import DiaryControls from "./DiaryControls";
import NewDiary from "./NewDiary";
import ViewDiaries from "./ViewDiaries";
import EditDiary from "./EditDiary";


export default function Diary({ username, onLogout }) {
   const [showNewDiary, setShowNewDiary] = useState(false);
   const [diaries, setDiaries] = useState([]);
   const [editingEntry, setEditingEntry] = useState(null);

  useEffect(() => {
    api.get("/diary")
      .then(res => setDiaries(res.data))
      .catch(console.error);
  }, []);

   const addDiary = (newEntry) => {
      setDiaries(prev => [newEntry, ...prev]);
      setShowNewDiary(false);  // optionally close form after adding
   };

   const handleDelete = async (id) => {
      try {
         await api.delete(`/diary/${id}`);
         setDiaries(prev => prev.filter(entry => entry.id !== id));
      } catch (err) {
         console.error(err);
      }
   };

    const handleEdit = (entry) => {
      setEditingEntry(entry); // sets form to open with existing values
   };

   const handleUpdate = async (updatedEntry) => {
      try {
         const res = await api.put(`/diary/${updatedEntry.id}`, updatedEntry);
         setDiaries(prev =>
            prev.map(entry =>
               entry.id === updatedEntry.id ? res.data : entry
            )
         );
         setEditingEntry(null); // close edit form
      } catch (err) {
         console.error("Update failed", err);
      }
   };

  return (
      <div className="min-h-screen bg-gray-900 text-white flex justify-center items-start p-4">
         <div className="w-full max-w-4xl">
            <DiaryControls username={username} onLogout={onLogout} />

            <button
               onClick={() => {
                  setShowNewDiary(!showNewDiary);
                  setEditingEntry(null); // close editing if open
               }}
               className="mb-4 bg-indigo-600 px-4 py-2 rounded hover:bg-indigo-500"
            >
               {showNewDiary ? "Cancel" : "Add New Entry"}
            </button>

            {showNewDiary && (
               <div className="mb-6">
                  <NewDiary onAdd={addDiary} />
               </div>
            )}

            {editingEntry && (
               <div className="mb-6">
                  <EditDiary
                     entry={editingEntry}
                     onCancel={() => setEditingEntry(null)}
                     onUpdate={handleUpdate}
                  />
               </div>
            )}

            <ViewDiaries diaries={diaries} onDelete={handleDelete} onEdit={handleEdit} />
         </div>
      </div>
   );

}
