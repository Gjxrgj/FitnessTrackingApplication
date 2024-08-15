/*
import React from "react";
import { useAuthContext } from "../components/AuthContext.tsx";
import {Route, RouteProps} from "react-router-dom";

interface Props extends React.VFC<RouteProps> {
  render: (props: any) => JSX.Element;
  exact?: boolean;
}

// @ts-ignore
function Redirect(props: { to: string }) {
  return null;
}

const ProtectedRoute = ({ render, exact = false, ...rest }: Props) => {
  const { isAuthenticated } = useAuthContext();
  return (
      <Route
          exact={exact}
          render={(props: any) => {
            if (!isAuthenticated) {
              return <Redirect to="/login" />;
            }
            return render(props);
          }}
          {...rest}
      />
  );
};

export default ProtectedRoute;
*/
