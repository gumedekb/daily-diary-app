import { useState, useEffect } from 'react'
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Login from './components/Login';
import Signup from "./components/Signup";
import Diary from "./components/Diary";
import { jwtDecode } from "jwt-decode"; 


const App = () => {
  const [username, setUsername] = useState(null);

  // on app load, check if token exists and optionally set username
  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const decoded = jwtDecode(token);
      setUsername(decoded.sub);
    }
  }, []);

  const handleLogin = (username) => setUsername(username);
  const handleLogout = () => setUsername(null);


  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            username ? (
              <Diary username={username} onLogout={handleLogout} />
            ) : (
              <Navigate to="/auth/login" />
            )
          }
        />
        <Route path="/auth/login" element={<Login onLogin={handleLogin} />} />
        <Route path="/auth/signup" element={<Signup />} />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;
