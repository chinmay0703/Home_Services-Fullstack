import React, { useState ,useEffect} from 'react'
import { motion } from 'framer-motion'
import { useNavigate } from 'react-router'
import { toast } from 'react-toastify'
import logo from '../../images/asy.png'
import axios from "axios";


import './booking.css'

export default function Bookings() {
 
    const [data, setData] = useState([]);
    useEffect(() => {
        const url = `http://localhost:3002/get`;
        axios.get(url).then((response) => {
          const result = response.data;
          setData(result);
          console.log(result.status);
        });
      }, []); 
      
   
    const scroolUp = () => {
        window.scrollTo(0, 0)
    }
    const navigate = useNavigate()
    const ask = () => {

        navigate("/Admin");

    };

    const logout = () => {
        toast.error("Logging Off")
        alert("Logging off");
        navigate("/");
    };
    const [approved, setApproved] = useState(false);
    const handleApprove = () => {
        setApproved(true);
    };
    return (
        <motion.div style={{ overflowX: "hidden" }} onLoad={scroolUp} className='fixedcontent'
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.3 }}
        >
            <div className="row shadow sticky-top"  >
                <nav className="navbar navbar-expand-lg" style={{ backgroundColor: "black" }}>
                    <div className="container-fluid">
                        <a className="navbar-brand" href='/Admin'
                        ><img src={logo} alt="" id='headerlogoProfile' /></a>
                        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" style={{ backgroundColor: "white" }}>
                            <span className="navbar-toggler-icon" style={{ backgroundColor: "grey" }}></span>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarSupportedContent">
                            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page" onClick={() => (ask())} id='headerBtn'>Home</a>
                                </li>

                            </ul>
                            <div className=''>
                                <motion.button className='btn btn-primary SignButton'
                                    whileHover={{ backgroundColor: "rgb(220, 222, 224)", color: "black" }}
                                    whileTap={{ backgroundColor: "rgb(220, 222, 224)", color: "black" }}
                                    onClick={() => (logout())}
                                >Logout</motion.button>
                            </div>
                        </div>
                    </div>
                </nav>
            </div >
            <br />
            <br></br>
            <br />
            <br />

            <div className='container'>
                <div className='row text-center table-responsive-xl'>
                    <table className='table table-hover table-dark'>
                        <thead>
                            <tr className='bg-warning'>
                                <th className='bg-primary'>ID</th>
                                <th >phoneno</th>
                                <th className='bg-primary'>address</th>
                                <th>date</th>
                                <th className='bg-primary'>rooms</th>
                                <th>Remove bookings</th>
                               
                            </tr>
                        </thead>
                        <tbody>
                            {data.map((item) => (
                                <tr key={item.id}>
                                    <td>{item.id}</td> {/* Replace 'name' with the actual property name */}
                                    <td>{item.phoneno}</td>
                                    <td>{item.address}</td>
                                    <td>{item.date}</td>
                                    <td>{item.rooms}</td>
                                    <td>

                                       
                                        <button className='btn btn-danger'>Delete</button>
                                                                      
                                    </td>
                            </tr>
                            ))}
                    </tbody>
                </table>
            </div>
        </div>

        </motion.div >

    );
}
