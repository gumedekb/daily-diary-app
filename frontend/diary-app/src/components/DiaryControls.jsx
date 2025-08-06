import React from "react";

const DiaryControls = ({ username, onLogout }) => {
  return (
    <div className="flex justify-between items-center bg-gray-800 p-4 rounded shadow">
      <h2 className="text-xl font-semibold">Welcome, {username}</h2>
      <button
        onClick={onLogout}
        className="bg-red-600 px-4 py-2 rounded hover:bg-red-500 transition"
      >
        Logout
      </button>
    </div>
  );
}

export default DiaryControls;
