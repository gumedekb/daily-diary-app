const ViewDiaries = ({ diaries, onDelete, onEdit }) => {
   return (
      <div className="bg-gray-800 p-6 rounded shadow">
         <h3 className="text-lg font-bold mb-4">Your Entries</h3>
         {diaries.length === 0 ? (
            <p>No entries found.</p>
         ) : (
            diaries.map((entry) => (
               <div key={entry.id} className="border-b border-gray-700 py-3">
                  <h4 className="font-semibold text-indigo-400">{entry.title}</h4>
                  <p className="text-sm text-gray-300">{entry.content}</p>
                  <small className="text-gray-500 block mb-2">
                     {new Date(entry.createdAt).toLocaleString()}
                  </small>
                  <div className="space-x-2">
                     <button
                        onClick={() => onEdit(entry)}
                        className="bg-yellow-500 px-2 py-1 rounded hover:bg-yellow-400"
                     >
                        Edit
                     </button>
                     <button
                        onClick={() => onDelete(entry.id)}
                        className="bg-red-600 px-3 py-1 rounded hover:bg-red-500"
                     >
                        Delete
                     </button>
                  </div>
               </div>
            ))
         )}
      </div>
   );
}

export default ViewDiaries;
