import {useLocation} from "react-router-dom";

export default function RequireAuth(){
    const location = useLocation();
    const auth = false;

    if(!auth){
        return
    }

    return (
        <div>

        </div>
    )
}