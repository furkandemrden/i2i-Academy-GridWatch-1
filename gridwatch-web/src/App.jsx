import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import Login from './pages/Login';
import HomeList from './pages/HomeList';
import HomeDetail from './pages/HomeDetail';
import NewHome from './pages/NewHome';

function ProtectedRoute({ children }) {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? children : <Navigate to="/login" replace />;
}

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <HomeList />
          </ProtectedRoute>
        }
      />
      <Route
        path="/homes/new"
        element={
          <ProtectedRoute>
            <NewHome />
          </ProtectedRoute>
        }
      />
      <Route
        path="/homes/:homeId"
        element={
          <ProtectedRoute>
            <HomeDetail />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}

export default App;